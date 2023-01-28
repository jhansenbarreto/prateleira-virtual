package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.HistoricoAtivacaoDesativacaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um histórico de ativação e desativação
 * de usuários.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface HistoricoUsuarioRepository extends JpaRepository<HistoricoAtivacaoDesativacaoUsuario, Long> {
}
