package com.prateleiravirtual.infrastructure.storage;

import static com.prateleiravirtual.PrateleiraVirtualApplication.GlobalConstants.*;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxSharedLink;
import com.box.sdk.sharedlink.BoxSharedLinkRequest;

import com.prateleiravirtual.domain.service.ImagemStorageService;
import com.prateleiravirtual.infrastructure.exception.CloudServiceException;
import com.prateleiravirtual.infrastructure.exception.StorageException;
import com.prateleiravirtual.domain.service.storage.CloudStorageService;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe que implementa a interface de armazenamento de arquivos de imagem,
 * responsável por armazenar, excluir e buscar imagens armazendas na nuvem.
 *
 * @author Jhansen Barreto
 */
public class ImagemCloudStorageImpl implements ImagemStorageService {

    @Autowired
    private CloudStorageService cloudService;

    /**
     * Método para armazenamento de arquivo de imagem. Responsável por fazer o
     * upload do arquivo para a nuvem.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @param inputStream (Stream do arquivo)
     * @return -> URL do arquivo já salvo na nuvem
     */
    @Override
    public String armazenar(String nomeArquivo, InputStream inputStream) {
        try (inputStream) {

            var api = cloudService.getAPI();

            var idDiretorio = diretorio(nomeArquivo, api);
            var novoArquivo = new BoxFolder(api, idDiretorio).uploadFile(inputStream, nomeArquivo);

            var id = novoArquivo.getID();
            var url = gerarURL(api, id);
            cloudService.saveFile(id, nomeArquivo, idDiretorio, url);

            return url;
        } catch (Exception ex) {
            throw new StorageException("Não foi possível salvar o arquivo: " + nomeArquivo, ex);
        }
    }

    /**
     * Método para exclusão de arquivo de imagem.
     *
     * @param nomeArquivo (Nome do arquivo a ser deletado)
     */
    @Override
    public void remover(String nomeArquivo) {
        try {
            var id = cloudService.getIdFile(nomeArquivo);
            new BoxFile(cloudService.getAPI(), id).delete();
            cloudService.deleteFile(id);

        } catch (Exception ex) {
            throw new StorageException("Não foi possível excluir o arquivo: " + nomeArquivo, ex);
        }
    }

    /**
     * Método para download do arquivo de imagem.
     *
     * @param nomeArquivo (Nome do arquivo a ser buscado)
     * @return -> Stream do arquivo solicitado
     */
    @Override
    public InputStream download(String nomeArquivo) {
        try {
            return new BoxFile(cloudService.getAPI(), cloudService.getIdFile(nomeArquivo))
                    .getDownloadURL()
                    .openStream();

        } catch (IOException ex) {
            throw new StorageException("Não foi possível encontrar o arquivo: " + nomeArquivo, ex);
        }
    }

    /**
     * Método para montar a URL que permite acessar o arquivo na nuvem.
     *
     * @param api (Objeto para conexão com a API de armazenamento)
     * @param id (Identificador do arquivo)
     * @return -> URL do arquivo
     */
    private String gerarURL(BoxAPIConnection api, String id) {
        var request = new BoxSharedLinkRequest()
                .access(BoxSharedLink.Access.OPEN)
                .permissions(true, true, false);

        return new BoxFile(api, id).createSharedLink(request).getDownloadURL();
    }

    /**
     * Método para retornar identificador do diretório onde será salvo o arquivo
     * de imagem.
     *
     * @param nomeArquivo (Nome do arquivo a ser salvo)
     * @return -> Identificador do diretório na nuvem
     */
    private String diretorio(String nomeArquivo, BoxAPIConnection api) {
        String nomeDiretorio;
        String idDiretorio;

        //BUSCANDO O NOME DA PASTA ATRAVÉS DO NOME DO ARQUIVO
        if (nomeArquivo.startsWith(AUTOR)) {
            nomeDiretorio = AUTOR;

        } else if (nomeArquivo.startsWith(OBRA)) {
            nomeDiretorio = OBRA;

        } else if (nomeArquivo.startsWith(USUARIO)) {
            nomeDiretorio = USUARIO;

        } else {
            throw new StorageException("Erro inesperado. Verificar nome de arquivo.");
        }

        try {
            //BUSCANDO ID DA PASTA, CASO JÁ EXISTA
            idDiretorio = cloudService.getIdFolder(nomeDiretorio);

        } catch (CloudServiceException ex) {
            //SE NÃO EXISTIR, CRIA NOVA PASTA
            idDiretorio = BoxFolder.getRootFolder(api).createFolder(nomeDiretorio).getID();
            cloudService.saveFolder(idDiretorio, nomeDiretorio);
        }
        return idDiretorio;
    }
}
