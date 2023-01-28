package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.EditoraNaoEncontradaException;
import com.prateleiravirtual.domain.exception.EntidadeEmUsoException;
import com.prateleiravirtual.domain.model.Editora;
import com.prateleiravirtual.domain.repository.EditoraRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada a classe Editora.
 *
 * @author Jhansen Barreto
 */
@Service
public class EditoraService {

    @Autowired
    private EditoraRepository repository;

    /**
     * Método para listar todos as Editora salvas.
     *
     * @return -> Lista de Editora
     */
    public List<Editora> listar() {
        return repository.findAll();
    }

    /**
     * Método para buscar uma única Editora. Caso não seja encontrada, uma
     * exceção é lançada.
     *
     * @param id (Identificador da Editora)
     * @return -> A Editora do ID informado, se existir.
     */
    public Editora buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new EditoraNaoEncontradaException(id));
    }

    /**
     * Método para pesquisar Editora por nome. Este método retorna uma lista
     * pois a busca pode ser por parte de uma palavra, até mesmo uma letra.
     *
     * @param nome (Palavra completa ou parcial de um nome)
     * @return -> Lista de Editora que contém parte ou todo o nome
     */
    public List<Editora> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome);
    }

    /**
     * Método para salvar uma Editora nova ou alterações de uma atualização.
     *
     * @param editora (Editora a ser salva)
     * @return -> Editora salva
     */
    @Transactional
    public Editora salvar(Editora editora) {
        return repository.save(editora);
    }

    /**
     * Método responsável por deletar uma Editora. Caso não exista o registro
     * desejado, uma exceção é lançada. Caso a editora esteja em uso, outra
     * exceção é lançada.
     *
     * @param id (Identificador da editora a ser deletada)
     */
    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new EditoraNaoEncontradaException(id);

        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    String.format("Editora de id %d está em uso e não pode ser removida", id));
        }
    }
}
