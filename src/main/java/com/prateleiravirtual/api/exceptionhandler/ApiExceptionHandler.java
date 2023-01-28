package com.prateleiravirtual.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import com.prateleiravirtual.domain.exception.EntidadeEmUsoException;
import com.prateleiravirtual.domain.exception.EntidadeNaoEncontradaException;
import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.unit.DataSize;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe global de tratamento de exceções. Qualquer exceção lançada na API é
 * tratada nesta classe.
 *
 * @author Jhansen Barreto
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private final static String MSG_GENERICA_ERRO
            = "ERRO INTERNO. Tente novamente e caso o erro persista, contate o administrador.";

    @Override
    //@ResponseStatus
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {

        var status = (HttpStatus) statusCode;

        if (body == null) {
            body = Error.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .detail(MSG_GENERICA_ERRO)
                    .timestamp(OffsetDateTime.now())
                    .build();

        } else if (body instanceof String stringBody) {
            body = Error.builder()
                    .status(status.value())
                    .title(stringBody)
                    .detail(MSG_GENERICA_ERRO)
                    .timestamp(OffsetDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    @Deprecated
    //@ResponseStatus
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        return handleValidationInternal(ex, status, headers, request, ex.getBindingResult());
    }

    @Override
    //@ResponseStatus
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return handleValidationInternal(ex, status, headers, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpStatusCode status,
            HttpHeaders headers, WebRequest request, BindingResult bindingResult) {

        var errors = bindingResult.getAllErrors().stream()
                .map(error -> {
                    String name = error.getObjectName();
                    if (error instanceof FieldError fieldError) {
                        name = fieldError.getField();
                    }
                    return Error.WrongObject.builder()
                            .name(name)
                            .message(messageSource.getMessage(error, LocaleContextHolder.getLocale()))
                            .build();
                })
                .collect(Collectors.toList());

        var detail = "Um ou mais campos estão inválidos. Corrija e tente novamente.";
        var erro = builderError((HttpStatus) status, ErrorType.DADOS_INVALIDOS, detail).errors(errors);

        return handleExceptionInternal(ex, erro.build(), headers, status, request);
    }

    @Override
    //@ResponseStatus
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        var detail = String.format("O recurso %s que você tentou acessar não existe", ex.getRequestURL());
        var erro = builderError((HttpStatus) status, ErrorType.RECURSO_NAO_ENCONTRADO, detail);

        return handleExceptionInternal(ex, erro.build(), headers, status, request);
    }

    @Override
    //@ResponseStatus
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var causaRaiz = ex.getRootCause();

        if (causaRaiz != null) {
            if (causaRaiz instanceof InvalidFormatException exception) {
                return handleInvalidFormat(exception, new HttpHeaders(), status, request);

            } else if (causaRaiz instanceof PropertyBindingException exception) {
                return handlePropertyBinding(exception, new HttpHeaders(), status, request);
            }
        }

        var erro = builderError((HttpStatus) status, ErrorType.CORPO_NAO_LEGIVEL,
                "Corpo da requisição não pôde ser entendido. Verifique a sintaxe.");

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        var detail = String.format(
                "O valor '%s' informado para a propriedade '%s' não é compatível com o tipo %s",
                ex.getValue(), joinPath(ex.getPath()), ex.getTargetType().getSimpleName());

        var erro = builderError((HttpStatus) status, ErrorType.CORPO_NAO_LEGIVEL, detail);

        return handleExceptionInternal(ex, erro.build(), headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        var detail = String.format("A propriedade '%s' não existe. Corrija e tente novamente.", joinPath(ex.getPath()));
        var erro = builderError((HttpStatus) status, ErrorType.PROPRIEDADE_NAO_EXISTE, detail);

        return handleExceptionInternal(ex, erro.build(), headers, status, request);
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s' que é incompatível com o tipo %s",
                ex.getParameter().getParameterName(), ex.getValue(), ex.getParameter().getParameterType().getSimpleName());

        var status = HttpStatus.BAD_REQUEST;
        var erro = builderError(status, ErrorType.PARAMETRO_INVALIDO, detail);

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = "Limite de tamanho excedido.";

        if (ex.getRootCause() instanceof FileSizeLimitExceededException exception) {
            detail += String.format(" O sistema não aceita upload de arquivos maiores que %dMB",
                    DataSize.ofBytes(exception.getPermittedSize()).toMegabytes());
        }
        Error.ErrorBuilder erro = builderError(status, ErrorType.DADOS_INVALIDOS, detail);

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var erro = builderError(status, ErrorType.RECURSO_NAO_ENCONTRADO, ex.getMessage());

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    //@ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var erro = builderError(status, ErrorType.ENTIDADE_EM_USO, ex.getMessage());

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ErroRegraNegocioException.class)
    public ResponseEntity<?> handleErroRegraNegocio(ErroRegraNegocioException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var erro = builderError(status, ErrorType.DADOS_INVALIDOS, ex.getMessage());

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var erro = builderError(status, ErrorType.ERRO_DE_SISTEMA, MSG_GENERICA_ERRO);

        return handleExceptionInternal(ex, erro.build(), new HttpHeaders(), status, request);
    }

    private Error.ErrorBuilder builderError(HttpStatus status, ErrorType type, String detail) {
        return Error.builder()
                .status(status.value())
                .title(type.getTitle())
                .detail(detail)
                .type(type.getUri())
                .timestamp(OffsetDateTime.now());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(item -> item.getFieldName())
                .collect(Collectors.joining("."));
    }
}
