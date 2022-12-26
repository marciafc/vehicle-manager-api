package br.com.marcia.application.core.exception;

import br.com.marcia.application.core.domain.Car;

public class CarAlreadyRegisteredException extends BusinessException {

    public CarAlreadyRegisteredException(Car car) {
        super(String.format("Já existe um carro cadastrado com o chassi %s ou placa %s.", car.getChassi(), car.getPlaca()));
    }
}
