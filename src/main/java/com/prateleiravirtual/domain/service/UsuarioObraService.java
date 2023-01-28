package com.prateleiravirtual.domain.service;

import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;
import com.prateleiravirtual.domain.model.Usuario;
import com.prateleiravirtual.domain.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviços relacionada ao vínculo de Usuario e Obra. Responsável por
 * adicionar ou remover obras das listas de leitura do usuário.
 *
 * @author Jhansen Barreto
 */
@Service
public class UsuarioObraService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ObraService obraService;

    /**
     * Método para vincular ou desvincular uma Obra de uma das listas de leitura
     * do Usuario.
     *
     * @param usuario (Usuario relacionado com a Obra)
     * @param obraId (Identificador da Obra)
     * @param tipo (Tipo de lista a ser vinculada ou desvinculada a obra)
     * @param vincular (true para vincular, false para desvincular)
     */
    @Transactional
    public void vincularOuDesvincularObra(Usuario usuario, Long obraId, TipoLista tipo, boolean vincular) {
        switch (tipo) {
            case OBRAS_LIDAS ->
                addOrRemoveObraLida(usuario, obraId, vincular);
            case OBRAS_PARA_LER ->
                addOrRemoveObraParaLer(usuario, obraId, vincular);
            case OBRAS_EM_LEITURA ->
                addOrRemoveObraEmLeitura(usuario, obraId, vincular);
            default ->
                throw new ErroRegraNegocioException("Tipo de lista inexistente.");
        }
        repository.flush();
    }

    /**
     * Método para vincular ou desvincular uma Obra da lista de obras já lidas.
     *
     * @param usuario (Usuario relacionado com a Obra)
     * @param obraId (Identificador da Obra)
     * @param add (true para adicionar, false para remover)
     */
    private void addOrRemoveObraLida(Usuario usuario, Long obraId, boolean add) {
        if (add) {
            usuario.getObrasLidas().add(obraService.buscar(obraId));
        } else {
            usuario.getObrasLidas().remove(obraService.buscar(obraId));
        }
    }

    /**
     * Método para vincular ou desvincular uma Obra da lista de obras para ler.
     *
     * @param usuario (Usuario relacionado com a Obra)
     * @param obraId (Identificador da Obra)
     * @param add (true para adicionar, false para remover)
     */
    private void addOrRemoveObraParaLer(Usuario usuario, Long obraId, boolean add) {
        if (add) {
            usuario.getObrasParaLer().add(obraService.buscar(obraId));
        } else {
            usuario.getObrasParaLer().remove(obraService.buscar(obraId));
        }
    }

    /**
     * Método para vincular ou desvincular uma Obra da lista de obras em leitura
     * (sendo lidas).
     *
     * @param usuario (Usuario relacionado com a Obra)
     * @param obraId (Identificador da Obra)
     * @param add (true para adicionar, false para remover)
     */
    private void addOrRemoveObraEmLeitura(Usuario usuario, Long obraId, boolean add) {
        if (add) {
            usuario.getObrasEmLeitura().add(obraService.buscar(obraId));
        } else {
            usuario.getObrasEmLeitura().remove(obraService.buscar(obraId));
        }
    }

    /**
     * Enumeração criada para facilitar a escolha de listas de leitura.
     */
    public enum TipoLista {
        OBRAS_LIDAS,
        OBRAS_PARA_LER,
        OBRAS_EM_LEITURA
    }
}
