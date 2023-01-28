package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção genérica para diversos erros da regra de negócio do
 * sistema.
 *
 * @author Jhansen Barreto
 */
public class ErroRegraNegocioException extends RuntimeException {

    public ErroRegraNegocioException() {
        super();
    }

    public ErroRegraNegocioException(String msg) {
        super(msg);
    }

    /**
     * Classe de constantes com algumas mensagens de erro pré definidas.
     */
    public static class ErroRegraNegocioMessages {

        public static final String CODIGO_EXPIRADO = "Código expirado. Peça o reenvio de um novo código para o seu e-mail.";
        public static final String USERNAME_CADASTRADO = "Nome de usuário já cadastrado.";
        public static final String EMAIL_CADASTRADO = "E-mail já cadastrado.";
        public static final String USERNAME_EMAIL_CADASTRADOS = "Nome de usuário e e-mail já cadastrados.";
        public static final String SENHA_ATUAL_INCORRETA = "A senha atual está incorreta.";
        public static final String CODIGO_INEXISTENTE = "Código inexistente.";
        public static final String USUARIO_ATIVADO = "Usuário já está ativado";
    }
}
