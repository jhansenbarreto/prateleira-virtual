package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.ObraNaoEncontradaException;
import com.prateleiravirtual.domain.model.ImagemDetalhes;
import com.prateleiravirtual.domain.model.ImagemObra;
import com.prateleiravirtual.domain.model.Obra;
import com.prateleiravirtual.domain.repository.ObraRepository;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada à classe Obra e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Service
public class ObraService {

    @Autowired
    private ObraRepository repository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditoraService editoraService;

    @Autowired
    private GeneroObraService generoService;

    @Autowired
    private ImagemStorageService storageService;

    /**
     * Método para listar todas as Obra salvas.
     *
     * @return -> Lista de Obra
     */
    public List<Obra> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar uma única Obra. Caso não seja encontrada, uma exceção
     * é lançada.
     *
     * @param id (Identificador da Obra)
     * @return -> A Obra do ID informado, se existir.
     */
    public Obra buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObraNaoEncontradaException(id));
    }

    /**
     * Método para pesquisar Obra por título e/ou subtítulo. Este método retorna
     * uma lista pois a busca pode ser por parte de uma palavra, até mesmo uma
     * letra.
     *
     * @param tituloSubtitulo (Palavra completa ou parcial do título ou
     * subtítulo de uma Obra)
     * @return -> Lista de Obra que contém parte ou toda a palavra no título
     * e/ou subtítulo
     */
    public List<Obra> buscarPorTituloSubtitulo(String tituloSubtitulo) {
        return repository.findByTituloOrSubtitulo(tituloSubtitulo);
    }

    /**
     * Método para salvar uma Obra nova ou alterações de uma atualização. Caso
     * seja desejado salvar a Obra já com os autores vinculados, basta informar
     * os ID's dos autores no parâmetro informado. Se o desejo for vincular os
     * autores posteriormente, é precisso passar o valor 'null' no parâmetro.
     *
     * @param obra (Obra a ser salva)
     * @param autorIds (Lista com identificadores de autores)
     * @return -> Obra salva
     */
    @Transactional
    public Obra salvar(Obra obra, List<Long> autorIds) {
        obra.setGenero(generoService.buscar(obra.getGenero().getId()));
        obra.setEditora(editoraService.buscar(obra.getEditora().getId()));

        if (autorIds != null && !autorIds.isEmpty()) {
            vincularAutores(obra, autorIds);
        }
        return repository.save(obra);
    }

    /**
     * Método responsável por deletar uma Obra. Caso não exista o registro
     * desejado, uma exceção é lançada.
     *
     * @param id (Identificador da Obra a ser deletada)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new ObraNaoEncontradaException(id);
        }
    }

    /**
     * Método responsável por salvar imagem de capa uma Obra. Salva o objeto no
     * banco de dados e o arquivo da imagem no disco local ou na nuvem,
     * dependendo da escolha feita no 'application.properties'
     *
     * @param obra (Obra associada a imagem)
     * @param detalhes (Objeto ImagemDetalhes da imagem da Obra)
     * @param inputStream (Stream do arquivo de imagem)
     */
    @Transactional
    public void salvarImagem(Obra obra, ImagemDetalhes detalhes, InputStream inputStream) {
        String nomeAnterior = null;

        //Se a obra já possuir uma imagem, ocorre a troca pela nova imagem
        if (obra.getCapa() != null) {
            nomeAnterior = obra.getCapa().getDetalhes().getNomeArquivo();
            obra.getCapa().setDetalhes(detalhes);
        } else {
            var imagem = new ImagemObra();
            imagem.setId(obra.getId());
            imagem.setDetalhes(detalhes);
            obra.setCapa(imagem);
        }
        repository.flush();

        //ADICIONANDO URL DE ACESSO ATRIBUÍDA À IMAGEM SALVA (DISCO OU NUVEM)
        obra.getCapa().getDetalhes().setUrl(
                storageService.atualizar(
                        obra.getCapa().getDetalhes().getNomeArquivo(),
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
     * Método para deletar imagem de capa de uma Obra. Remove não só do banco de
     * dados mas também apaga o arquivo onde estiver armazenado.
     *
     * @param obra (Obra com Imagem associada)
     */
    @Transactional
    public void excluirImagem(Obra obra) {
        var nomeArquivo = obra.getCapa().getDetalhes().getNomeArquivo();

        obra.setCapa(null);
        repository.flush();

        repository.deleteImagemCapa(obra.getId());
        repository.flush();

        storageService.remover(nomeArquivo);
    }

    /**
     * Método para vincular um Autor a uma Obra
     *
     * @param obra (Obra que receberá o Autor)
     * @param autorId (Identificador do Autor relacionado a Obra)
     */
    @Transactional
    public void vincularAutor(Obra obra, Long autorId) {
        obra.getAutores().add(autorService.buscar(autorId));
    }

    /**
     * Método para desvincular um Autor de uma Obra
     *
     * @param obra (Obra que terá o Autor removido)
     * @param autorId (Identificador do Autor relacionado a Obra)
     */
    @Transactional
    public void desvincularAutor(Obra obra, Long autorId) {
        obra.getAutores().remove(autorService.buscar(autorId));
    }

    /**
     * Método para vincular muitos objetos Autor a uma Obra
     *
     * @param obra (Obra que receberá os autores)
     * @param autorIds (Lista com identificadores de Autor relacionados a Obra)
     */
    @Transactional
    public void vincularAutores(Obra obra, List<Long> autorIds) {
        autorIds.forEach(autorId -> vincularAutor(obra, autorId));
    }

    /**
     * Método para desvincular muitos objetos Autor de uma Obra
     *
     * @param obra (Obra que terá os autores removidos)
     * @param autorIds (Lista com identificadores de Autor relacionados a Obra)
     */
    @Transactional
    public void desvincularAutores(Obra obra, List<Long> autorIds) {
        autorIds.forEach(autorId -> desvincularAutor(obra, autorId));
    }
}
