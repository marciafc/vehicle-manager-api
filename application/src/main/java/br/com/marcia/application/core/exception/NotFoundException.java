package br.com.marcia.application.core.exception;

public class NotFoundException  extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
