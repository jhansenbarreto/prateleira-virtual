package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.model.AcaoHistorico;
import com.prateleiravirtual.domain.model.HistoricoAtivacaoDesativacaoUsuario;
import com.prateleiravirtual.domain.model.Usuario;
import com.prateleiravirtual.domain.repository.HistoricoUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço relacionada à classe de HistoricoUsuario.
 *
 * @author Jhansen Barreto
 */
@Service
public class HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioRepository repository;

    /**
     * Método para salvar o histórico dos usuários, relacionado às ações de
     * ativação e desativação de Usuario.
     *
     * @param acao (Ação feita pelo Usuario)
     * @param usuario (Usuario relacionado ao histórico)
     * @return -> HistoricoUsuario salvo
     */
    @Transactional
    public HistoricoAtivacaoDesativacaoUsuario salvar(AcaoHistorico acao, Usuario usuario) {
        var historico = HistoricoAtivacaoDesativacaoUsuario.builder()
                .acao(acao)
                .usuario(usuario)
                .build();

        return repository.save(historico);
    }
}
