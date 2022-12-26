package br.com.marcia.application.ports.out;

import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.filters.CarFilter;

import java.util.List;

public interface CarPort {

    Car insert(Car car);

    List<Car> findAllWithOperatorOr(CarFilter carFilter);

    Car getById(Long id);

    void changeStatus(Car car, VehicleStatusEnum status);

    void delete(Car car);
}
