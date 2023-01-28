package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.AtivacaoUsuario;
import com.prateleiravirtual.domain.model.RedefinicaoSenha;
import com.prateleiravirtual.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um usuário e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface UsuarioRepository extends
        CustomJPARepository<Usuario, Long>, AtivacaoUsuarioQueries, RedefinicaoSenhaQueries {

    /**
     * Método que retorna uma lista de Usuario, pesquisados por username.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de Usuario
     */
    List<Usuario> findByUsernameContaining(String search);

    /**
     * Método para retornar Usuario buscado por e-mail.
     *
     * @param email (E-mail a ser pesquisado)
     * @return -> Optional podendo conter o Usuario desejado ou vazio.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Método que retorna uma lista de Usuario, pesquisados por e-mail ou
     * username.
     *
     * @param email (E-mail a ser pesquisado)
     * @param username (Username a ser pesquisado)
     * @return -> Lista de Usuario
     */
    @Query("FROM Usuario u WHERE u.email = :email OR u.username = :username")
    List<Usuario> findByEmailOrUsername(String email, String username);

    /**
     * Método para deletar a Imagem de um Usuario.
     *
     * @param usuarioId (Identificador do Usuario)
     */
    @Modifying
    @Query("DELETE FROM ImagemUsuario img WHERE img.id = :usuarioId")
    void deleteImagem(Long usuarioId);

    /**
     * Método para retornar um objeto AtivacaoUsuario, buscado através do ID do
     * Usuario.
     *
     * @param usuarioId (Identificador do Usuario)
     * @return -> Optional podendo conter a AtivacaoUsuario desejada ou vazio.
     */
    @Query("FROM AtivacaoUsuario a WHERE a.id = :usuarioId")
    Optional<AtivacaoUsuario> findAtivacaoById(Long usuarioId);

    /**
     * Método para deletar uma AtivacaoUsuario através do ID de um Usuario.
     *
     * @param usuarioId (Identificador do Usuario)
     */
    @Modifying
    @Query("DELETE FROM AtivacaoUsuario a WHERE a.id = :usuarioId")
    void deleteAtivacao(Long usuarioId);

    /**
     * Método para retornar um objeto RedefinicaoSenha, buscado através do ID do
     * Usuario.
     *
     * @param usuarioId (Identificador do Usuario)
     * @return
     */
    @Query("FROM RedefinicaoSenha rs WHERE rs.id = :usuarioId")
    Optional<RedefinicaoSenha> findRedefinicaoById(Long usuarioId);

    /**
     * Método para deletar uma RedefinicaoSenha através do ID de um Usuario.
     *
     * @param usuarioId (Identificador do Usuario)
     */
    @Modifying
    @Query("DELETE FROM RedefinicaoSenha rs WHERE rs.id = :usuarioId")
    void deleteRedefinicao(Long usuarioId);
}
