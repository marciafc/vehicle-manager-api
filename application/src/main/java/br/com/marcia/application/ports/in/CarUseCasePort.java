package br.com.marcia.application.ports.in;

import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.filters.CarFilter;

import java.util.List;
import java.util.Map;

public interface CarUseCasePort {

    Car insert(Car car);

    Map<String, List<Car>> findAllGroupByManufacturer(CarFilter carFilter);

    Car getById(Long id);

    void changeStatus(Long id, VehicleStatusEnum status);

    void delete(Long id);
}
