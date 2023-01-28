package com.prateleiravirtual.api.controller.utils;

import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Classe de utilidades do pacote controller.
 *
 * @author Jhansen Barreto
 */
@Component
public class Utilidades {

    private final String MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private final String ALGARISMOS = "0123456789";
    private final String SIMBOLOS = "!@#$&*+";
    private final String CHARS = (MAIUSCULAS + MINUSCULAS + ALGARISMOS + SIMBOLOS);

    /**
     * Método gerador de sequência aleatória de caracteres. Pode ser utilizado
     * até para geração de senhas.
     *
     * @param lenght (quantidade de caracteres da cadeia >= 8)
     * @return -> Sequência gerada aleatoriamente
     */
    public String gerarCharsAleatorios(int lenght) {
        if (lenght < 8) {
            throw new ErroRegraNegocioException("Não é permitido um tamanho menor que 8.");
        }
        var random = new SecureRandom();
        var chars = new StringBuilder();

        //escolhendo pelo menos 1 maiúscula, 1 minúscula, 1 algarismo e 1 símbolo
        chars.append(MAIUSCULAS.charAt(random.nextInt(MAIUSCULAS.length())));
        chars.append(MINUSCULAS.charAt(random.nextInt(MINUSCULAS.length())));
        chars.append(ALGARISMOS.charAt(random.nextInt(ALGARISMOS.length())));
        chars.append(SIMBOLOS.charAt(random.nextInt(SIMBOLOS.length())));

        //decrementando o tamanho da senha pela quantidade de caracteres escolhidos
        lenght -= chars.length();

        //adicionando novos caracteres
        for (int i = 0; i < lenght; i++) {
            chars.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return embaralhar(chars.toString());
    }

    /**
     * Método para embaralhar Strings.
     *
     * @param original (String que deseja embaralhar)
     * @return -> String embaralhada
     */
    private String embaralhar(String original) {
        var list = original.chars().boxed().map(c -> (char) c.intValue()).collect(Collectors.toList());
        var embaralhada = new StringBuilder();

        Collections.shuffle(list);
        list.forEach(embaralhada::append);

        return embaralhada.toString();
    }

    /**
     * Método que gera o nome de um arquivo concatenando um prefixo (opcional)
     * com um código UUID, mantendo a extensão original do arquivo;
     *
     * @param prefixo (algum identificador que se deseje acrescentar ao arquivo)
     * @param nomeOriginal (nome original e completo do arquivo)
     * @return -> Novo nome de arquivo
     */
    public String gerarNomeArquivo(String prefixo, String nomeOriginal) {
        if (prefixo == null) {
            prefixo = "";
        }
        return String.format("%s_%s.%s",
                prefixo,
                UUID.randomUUID(),
                extrairExtensao(nomeOriginal));
    }

    /**
     * Método que retorna a extensão do arquivo.
     *
     * @param nomeArquivo (nome completo do arquivo)
     * @return -> Extensão do arquivo. Exemplos: png, jpg, pdf
     */
    private String extrairExtensao(String nomeArquivo) {
        var array = nomeArquivo.split("\\.");

        if (array.length < 1) {
            throw new ErroRegraNegocioException("Não foi possível extrair a extensão do arquivo.");
        }
        return array[(array.length - 1)];
    }
}
