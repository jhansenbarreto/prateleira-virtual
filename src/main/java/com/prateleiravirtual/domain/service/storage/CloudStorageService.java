package com.prateleiravirtual.domain.service.storage;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxConfig;
import com.box.sdk.BoxDeveloperEditionAPIConnection;

import com.prateleiravirtual.core.crypto.Crypto;
import com.prateleiravirtual.infrastructure.exception.CloudServiceException;

import com.prateleiravirtual.domain.model.storage.CloudClient;
import com.prateleiravirtual.domain.model.storage.CloudFile;
import com.prateleiravirtual.domain.model.storage.CloudFolder;

import com.prateleiravirtual.domain.repository.storage.CloudClientRepository;
import com.prateleiravirtual.domain.repository.storage.CloudFileRepository;
import com.prateleiravirtual.domain.repository.storage.CloudFolderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço relacionada ao armazenamento em nuvem.
 *
 * @author Jhansen Barreto
 */
@Service
public class CloudStorageService {

    @Autowired
    private CloudClientRepository clientRepository;

    @Autowired
    private CloudFolderRepository folderRepository;

    @Autowired
    private CloudFileRepository fileRepository;

    private String token = null;
    private CloudClient client = null;
    private BoxAPIConnection api = null;

    @Autowired
    private Crypto crypto;

    /**
     * Este método é responsável por receber a String que contém os requisitos
     * de configuração para utilizar um cliente da API Box, serviço de
     * armazenamento em nuvem. A String é criptografada e os bytes são salvos no
     * banco de dados.
     *
     * @param json (String que contém os requisitos de configuração)
     */
    @Transactional
    public void saveBoxConfig(String json) {
        var newClient = new CloudClient();
        newClient.setBytes(crypto.encrypt(json));
        clientRepository.save(newClient);
    }

    /**
     * Este método retorna o objeto que representa a conexão com a API Box.
     *
     * @return -> Objeto do tipo BoxAPIConnection
     */
    public BoxAPIConnection getAPI() {
        if (api == null) {
            api = getConnection();
        }
        return api;
    }

    /**
     * Este método é responsável por instanciar o objeto de conexão com a API
     * Box e fazer as configurações necessárias.
     *
     * @return -> Objeto do tipo BoxAPIConnection
     */
    private BoxAPIConnection getConnection() {
        var config = BoxConfig.readFrom(crypto.decrypt(getClient().getBytes()));
        var connection = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);

        if (token == null) {
            token = connection.getAccessToken();
        }
        return new BoxAPIConnection(token);
    }

    /**
     * Este método retorna o objeto que contém as informações necessárias para a
     * configuração de um cliente da API Box.
     *
     * @return -> Objeto necessário para configurar conexão com a API Box
     */
    private CloudClient getClient() {
        if (client == null) {
            //buscando cliente da API com ID 1 pois não existirá outro registro no banco de dados
            client = clientRepository.findById(1L).orElseThrow(()
                    -> new CloudServiceException("Client API Box not found"));
        }
        return client;
    }

    /**
     * Este método é responsável por salvar as informações de um arquivo que já
     * foi enviado para a nuvem através de upload.
     *
     * @param id (Identificador do arquivo)
     * @param nameFile (Nome do arquivo)
     * @param folderId (Identificador da pasta onde está salvo o arquivo)
     * @param url (URL de acesso ao arquivo)
     */
    @Transactional
    public void saveFile(String id, String nameFile, String folderId, String url) {
        fileRepository.save(new CloudFile(id, nameFile, findFolderById(folderId), url));
    }

    /**
     * Estse método é responsável por salvar as informações de pastas criadas na
     * nuvem.
     *
     * @param id (Identificador da pasta)
     * @param nameFolder (Nome da pasta)
     */
    @Transactional
    public void saveFolder(String id, String nameFolder) {
        folderRepository.save(new CloudFolder(id, nameFolder));
    }

    /**
     * Este método retorna o objeto que contém as informações de uma pasta que
     * existe na nuvem através do seu identificador. Caso o registro não exista
     * no banco de dados, uma exceção é lançada.
     *
     * @param folderId (Identificador da pasta)
     * @return -> Objeto do tipo CloudFolder
     */
    private CloudFolder findFolderById(String folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new CloudServiceException("Directory not found"));
    }

    /**
     * Este método retorna o identificador de uma pasta de acordo com seu nome.
     * Caso a pasta não exista, uma exceção é lançada.
     *
     * @param nameFolder (Nome da pasta)
     * @return -> Identificador da pasta
     */
    public String getIdFolder(String nameFolder) {
        return folderRepository.findByName(nameFolder)
                .orElseThrow(() -> new CloudServiceException("Directory not found"))
                .getId();
    }

    /**
     * Este método retorna o identificador de um arquivo de acordo com seu nome.
     * Caso o arquivo não exista, uma exceção é lançada.
     *
     * @param nameFile (Nome do arquivo)
     * @return -> Identificador do arquivo
     */
    public String getIdFile(String nameFile) {
        return fileRepository.findByName(nameFile)
                .orElseThrow(() -> new CloudServiceException("File not found"))
                .getId();
    }

    /**
     * Método responsável por deletar o registro de um arquivo do banco de
     * dados. Caso não exista o registro desejado, uma exceção é lançada.
     *
     * @param idFile (Identificador do arquivo)
     */
    @Transactional
    public void deleteFile(String idFile) {
        try {
            fileRepository.deleteById(idFile);
            fileRepository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new CloudServiceException("File not found", ex);
        }
    }
}
