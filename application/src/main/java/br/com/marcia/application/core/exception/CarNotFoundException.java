package br.com.marcia.application.core.exception;

public class CarNotFoundException extends NotFoundException {

    public CarNotFoundException(Long id) {
        super(String.format("Não foi localizado um carro com o id %s.", id));
    }
}
