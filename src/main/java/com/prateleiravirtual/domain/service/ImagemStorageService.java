package com.prateleiravirtual.domain.service;

import java.io.InputStream;

/**
 * Interface responsável por definir os métodos relacionados ao armazenamento de
 * arquivos. Ver implementações.
 *
 * @author Jhansen Barreto
 */
public interface ImagemStorageService {

    /**
     * Método para gravar arquivo. Ver implementações.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @param inputStream (Stream do arquivo)
     * @return -> Caminho/URL do arquivo
     */
    String armazenar(String nomeArquivo, InputStream inputStream);

    /**
     * Método para apagar arquivo. Ver implementações.
     *
     * @param nomeArquivo (Nome do arquivo)
     */
    void remover(String nomeArquivo);

    /**
     * Método para servir o stream do arquivo para download.
     *
     * @param nomeArquivo (Nome do arquivo)
     * @return -> Stream do arquivo
     */
    InputStream download(String nomeArquivo);

    /**
     * Método para substiruir um arquivo, caso exista uma versão antiga. Se
     * existir, é obrigatório informar o nome do mesmo. Caso contrário, é
     * preciso passar o valor 'null' no parâmetro.
     *
     * @param nomeArquivo (Nome do novo arquivo)
     * @param inputStream (Stream do novo arquivo)
     * @param nomeArquivoAnterior (Nome do arquivo antigo)
     * @return -> Caminho/URL do arquivo
     */
    default String atualizar(String nomeArquivo, InputStream inputStream, String nomeArquivoAnterior) {
        var url = this.armazenar(nomeArquivo, inputStream);

        //REMOVER SÓ APÓS O ARMAZENAMENTO
        if (nomeArquivoAnterior != null) {
            this.remover(nomeArquivoAnterior);
        }
        return url;
    }
}
