package com.prateleiravirtual.core.crypto;

import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;
import jakarta.validation.constraints.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Classe responsável por criptografia e decriptografia de strings.
 *
 * @author Jhansen Barreto
 */
@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("prateleira-virtual.crypto")
public class Crypto {

    @NotNull
    private String key;

    private SecretKeySpec chave;
    private Cipher cipher;

    /**
     * Método para criptografia de String.
     *
     * @param plaintext (String "aberta")
     * @return -> Array de bytes já criptografado.
     */
    public byte[] encrypt(String plaintext) {
        try {
            chave = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, chave);

            var bytes = plaintext.getBytes();
            return cipher.doFinal(bytes);

        } catch (InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
                | UnsupportedEncodingException
                | NoSuchAlgorithmException
                | NoSuchPaddingException ex) {
            throw new ErroRegraNegocioException("Error encrypt plaintext");
        }
    }

    /**
     * Método para decriptografar o array de bytes para a String original.
     *
     * @param bytes (Array de bytes criptografado)
     * @return -> String original
     */
    public String decrypt(byte[] bytes) {
        try {
            chave = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, chave);

            return new String(cipher.doFinal(bytes));

        } catch (InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
                | UnsupportedEncodingException
                | NoSuchAlgorithmException
                | NoSuchPaddingException ex) {
            throw new ErroRegraNegocioException("Error decrypt bytes");
        }
    }
}
