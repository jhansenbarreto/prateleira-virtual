package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.TipoObraNaoEncontradoException;
import com.prateleiravirtual.domain.exception.EntidadeEmUsoException;
import com.prateleiravirtual.domain.model.TipoObra;
import com.prateleiravirtual.domain.repository.TipoObraRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada a classe TipoObra.
 *
 * @author Jhansen Barreto
 */
@Service
public class TipoObraService {

    @Autowired
    private TipoObraRepository repository;

    /**
     * Método para listar todos os TipoObra salvos.
     *
     * @return -> Lista de TipoObra
     */
    public List<TipoObra> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar um único TipoObra. Caso não seja encontrado, uma
     * exceção é lançada.
     *
     * @param id (Identificador do TipoObra)
     * @return -> O TipoObra do ID informado, se existir.
     */
    public TipoObra buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new TipoObraNaoEncontradoException(id));
    }

    /**
     * Método para pesquisar TipoObra por descrição. Este método retorna uma
     * lista pois a busca pode ser por parte de uma palavra, até mesmo uma
     * letra.
     *
     * @param descricao (Palavra completa ou parcial de uma descrição)
     * @return -> Lista de TipoObra que contém parte ou toda a descricao
     */
    public List<TipoObra> buscarPorDescricao(String descricao) {
        return repository.findByDescricaoContaining(descricao);
    }

    /**
     * Método para salvar um TipoObra novo ou alterações de uma atualização.
     *
     * @param tipo (TipoObra a ser salvo)
     * @return -> TipoObra salvo
     */
    @Transactional
    public TipoObra salvar(TipoObra tipo) {
        return repository.save(tipo);
    }

    /**
     * Método responsável por deletar um TipoObra. Caso não exista o registro
     * desejado, uma exceção é lançada. Caso o tipo esteja em uso, outra exceção
     * é lançada.
     *
     * @param id (Identificador do tipo a ser deletado)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new TipoObraNaoEncontradoException(id);

        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Tipo de id %d está em uso e não pode ser removido", id));
        }
    }
}
