package com.prateleiravirtual.infrastructure.storage;

import static com.prateleiravirtual.PrateleiraVirtualApplication.GlobalConstants.*;

import com.prateleiravirtual.core.properties.StorageProperties;
import com.prateleiravirtual.domain.service.ImagemStorageService;
import com.prateleiravirtual.infrastructure.exception.StorageException;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

/**
 * Classe que implementa a interface de armazenamento de arquivos de imagem,
 * responsável por armazenar, excluir e buscar imagens armazendas localmente.
 *
 * @author Jhansen Barreto
 */
public class ImagemLocalStorageImpl implements ImagemStorageService {

    @Autowired
    private StorageProperties properties;

    /**
     * Método para armazenamento de arquivo de imagem. Responsável por gravar o
     * arquivo em disco.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @param inputStream (Stream do arquivo)
     * @return -> Caminho do arquivo já salvo no disco local
     */
    @Override
    public String armazenar(String nomeArquivo, InputStream inputStream) {
        var caminho = diretorio(nomeArquivo);

        try {
            FileCopyUtils.copy(inputStream, Files.newOutputStream(caminho));
        } catch (IOException ex) {
            throw new StorageException("Não foi possível salvar o arquivo: " + nomeArquivo, ex);
        }
        return caminho.toString();
    }

    /**
     * Método para exclusão de arquivo de imagem.
     *
     * @param nomeArquivo (Nome do arquivo a ser deletado)
     */
    @Override
    public void remover(String nomeArquivo) {
        try {
            Files.deleteIfExists(diretorio(nomeArquivo));
        } catch (IOException ex) {
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
            return Files.newInputStream(diretorio(nomeArquivo));
        } catch (IOException ex) {
            throw new StorageException("Não foi possível encontrar o arquivo: " + nomeArquivo, ex);
        }
    }

    /**
     * Método para retornar o caminho onde o arquivo será salvo no disco local.
     *
     * @param nomeArquivo (Nome do arquivo a ser salvo)
     * @return -> Caminho para salvar o arquivo de imagem
     */
    private Path diretorio(String nomeArquivo) {

        //BUSCANDO QUAL O CAMINHO CORRETO ATRAVÉS DO NOME DO ARQUIVO
        if (nomeArquivo.startsWith(AUTOR)) {
            return properties.getLocalDisk().getDiretorioImagemAutor().resolve(nomeArquivo);

        } else if (nomeArquivo.startsWith(OBRA)) {
            return properties.getLocalDisk().getDiretorioImagemObra().resolve(nomeArquivo);

        } else if (nomeArquivo.startsWith(USUARIO)) {
            return properties.getLocalDisk().getDiretorioImagemUser().resolve(nomeArquivo);

        } else {
            throw new StorageException("Erro inesperado. Verificar nome de arquivo.");
        }
    }
}
