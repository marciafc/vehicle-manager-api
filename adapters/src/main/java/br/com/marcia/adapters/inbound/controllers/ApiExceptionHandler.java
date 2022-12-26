package br.com.marcia.adapters.inbound.controllers;

import br.com.marcia.application.core.exception.BusinessException;
import br.com.marcia.application.core.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private final String BAD_REQUEST_MESSAGE = "Requisição inválida.";
    private final String INTERNAL_SERVER_ERROR_MESSAGE = "Ocorreu um erro no servidor.";

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException ex) {
        log.info("Erro de requisição inválida.", ex);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleArgumentValidationException(MethodArgumentNotValidException ex) {
        log.info("Erro de requisição inválida.", ex);
        return handleBadRequest(ex.getBindingResult());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        log.info("Erro de requisição inválida.", ex);
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handleBindException(BindException ex) {
        log.info("Erro de requisição inválida.", ex);
        return handleBadRequest(ex);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        log.error("Erro não tratado.", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    private ErrorResponse handleBadRequest(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult
                .getFieldErrors()
                .stream()
                .map(e -> new FieldError(e.getField(), e.getDefaultMessage()))
                .collect(toList());
        return new ErrorResponseWithFields(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, errors);
    }

    @Getter
    class ErrorResponseWithFields extends ErrorResponse {
        private List<FieldError> erros;

        public ErrorResponseWithFields(HttpStatus status, String message, List<FieldError> errors) {
            super(status, message);
            this.erros = errors;
        }
    }

    @Getter
    class ErrorResponse {
        private Integer status;
        private String error;
        private String message;
        private Instant timestamp;

        public ErrorResponse(HttpStatus status, String message) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.timestamp = Instant.now();
        }
    }

    @AllArgsConstructor
    @Getter
    class FieldError {
        private String field;
        private String message;
    }
}
