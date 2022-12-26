package br.com.marcia.application.core.exception;

public class CarListNotFoundException extends NotFoundException {

    public CarListNotFoundException() {
        super(String.format("Nenhum carro foi encontrado com os parâmetros informados."));
    }
}
