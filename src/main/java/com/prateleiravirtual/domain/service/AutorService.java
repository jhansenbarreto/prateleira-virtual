package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.AutorNaoEncontradoException;
import com.prateleiravirtual.domain.exception.EntidadeEmUsoException;
import com.prateleiravirtual.domain.model.Autor;
import com.prateleiravirtual.domain.model.ImagemDetalhes;
import com.prateleiravirtual.domain.model.ImagemAutor;
import com.prateleiravirtual.domain.repository.AutorRepository;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada à classe Autor e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Service
public class AutorService {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private ImagemStorageService storageService;

    /**
     * Método para listar todos os Autor salvos.
     *
     * @return -> Lista de Autor
     */
    public List<Autor> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar um único Autor. Caso não seja encontrado, uma exceção
     * é lançada.
     *
     * @param id (Identificador do Autor)
     * @return -> O Autor do ID informado, se existir.
     */
    public Autor buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new AutorNaoEncontradoException(id));
    }

    /**
     * Método para pesquisar Autor por nome. Este método retorna uma lista pois
     * a busca pode ser por parte de uma palavra, até mesmo uma letra.
     *
     * @param nome (Palavra completa ou parcial do nome de um Autor)
     * @return -> Lista de Autor que contém parte ou todo o nome
     */
    public List<Autor> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome);
    }

    /**
     * Método para salvar um Autor novo ou alterações de uma atualização.
     *
     * @param autor (Autor a ser salvo)
     * @return -> Autor salvo
     */
    @Transactional
    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

    /**
     * Método responsável por deletar um Autor. Caso o autor esteja em uso, uma
     * exceção é lançada. Caso não exista o registro desejado, outra exceção é
     * lançada.
     *
     * @param id (Identificador do tipo a ser deletado)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            if (!buscar(id).getObras().isEmpty()) {
                throw new EntidadeEmUsoException(
                        String.format("Autor de id %d está em uso e não pode ser removido", id));
            }
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new AutorNaoEncontradoException(id);
        }
    }

    /**
     * Método responsável por salvar imagem de um Autor. Salva o objeto no banco
     * de dados e o arquivo da imagem no disco local ou na nuvem, dependendo da
     * escolha feita no 'application.properties'
     *
     * @param autor (Autor associado a imagem)
     * @param detalhes (Objeto ImagemDetalhes da imagem do Autor)
     * @param inputStream (Stream do arquivo de imagem)
     */
    @Transactional
    public void salvarImagem(Autor autor, ImagemDetalhes detalhes, InputStream inputStream) {
        String nomeAnterior = null;

        //Se o autor já possuir uma imagem, ocorre a troca pela nova imagem
        if (autor.getImagem() != null) {
            nomeAnterior = autor.getImagem().getDetalhes().getNomeArquivo();
            autor.getImagem().setDetalhes(detalhes);
        } else {
            var imagem = new ImagemAutor();
            imagem.setId(autor.getId());
            imagem.setDetalhes(detalhes);
            autor.setImagem(imagem);
        }
        repository.flush();

        //ADICIONANDO URL DE ACESSO ATRIBUÍDA À IMAGEM SALVA (DISCO OU NUVEM)
        autor.getImagem().getDetalhes().setUrl(
                storageService.atualizar(
                        autor.getImagem().getDetalhes().getNomeArquivo(),
                        inputStream,
                        nomeAnterior
                )
        );
    }

    /**
     * Método responsável por buscar uma determinada imagem, servindo o stream
     * do arquivo para um possível download.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @return -> Stream do arquivo informado
     */
    public InputStream buscarImagem(String nomeArquivo) {
        return storageService.download(nomeArquivo);
    }

    /**
     * Método para deletar imagem de um Autor. Remove não só do banco de dados
     * mas também apaga o arquivo onde estiver armazenado.
     *
     * @param autor (Autor com Imagem associada)
     */
    @Transactional
    public void excluirImagem(Autor autor) {
        var nomeArquivo = autor.getImagem().getDetalhes().getNomeArquivo();

        autor.setImagem(null);
        salvar(autor);
        repository.flush();

        repository.deleteImagem(autor.getId());
        repository.flush();

        storageService.remover(nomeArquivo);
    }
}
