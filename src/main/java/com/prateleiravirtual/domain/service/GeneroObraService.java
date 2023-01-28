package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.GeneroObraNaoEncontradoException;
import com.prateleiravirtual.domain.exception.EntidadeEmUsoException;
import com.prateleiravirtual.domain.model.GeneroObra;
import com.prateleiravirtual.domain.repository.GeneroObraRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada a classe GeneroObra.
 *
 * @author Jhansen Barreto
 */
@Service
public class GeneroObraService {

    @Autowired
    private GeneroObraRepository repository;

    /**
     * Método para listar todos os GeneroObra salvos.
     *
     * @return -> Lista de GeneroObra
     */
    public List<GeneroObra> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar um único GeneroObra. Caso não seja encontrado, uma
     * exceção é lançada.
     *
     * @param id (Identificador do GeneroObra)
     * @return -> O GeneroObra do ID informado, se existir.
     */
    public GeneroObra buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new GeneroObraNaoEncontradoException(id));
    }

    /**
     * Método para pesquisar GeneroObra por descrição. Este método retorna uma
     * lista pois a busca pode ser por parte de uma palavra, até mesmo uma
     * letra.
     *
     * @param descricao (Palavra completa ou parcial de uma descrição)
     * @return -> Lista de GeneroObra que contém parte ou toda a descricao
     */
    public List<GeneroObra> buscarPorDescricao(String descricao) {
        return repository.findByDescricaoContaining(descricao);
    }

    /**
     * Método para salvar um GeneroObra novo ou alterações de uma atualização.
     *
     * @param genero (GeneroObra a ser salvo)
     * @return -> GeneroObra salvo
     */
    @Transactional
    public GeneroObra salvar(GeneroObra genero) {
        return repository.save(genero);
    }

    /**
     * Método responsável por deletar um GeneroObra. Caso não exista o registro
     * desejado, uma exceção é lançada. Caso o gênero esteja em uso, outra
     * exceção é lançada.
     *
     * @param id (Identificador do gênero a ser deletado)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new GeneroObraNaoEncontradoException(id);

        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Gênero de id %d está em uso e não pode ser removido", id));
        }
    }
}
