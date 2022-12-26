package br.com.marcia.application.core.usecase;

import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.exception.CarAlreadyRegisteredException;
import br.com.marcia.application.core.exception.CarListNotFoundException;
import br.com.marcia.application.core.exception.CarNotFoundException;
import br.com.marcia.application.core.filters.CarFilter;
import br.com.marcia.application.ports.in.CarUseCasePort;
import br.com.marcia.application.ports.out.CarPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class CarUserCase implements CarUseCasePort {

    private final CarPort carPort;

    public Car insert(Car car) {

        var carAlreadyRegistered = carPort.findAllWithOperatorOr(CarFilter.builder()
                .chassi(car.getChassi())
                .placa(car.getPlaca())
                .build());

        if(carAlreadyRegistered  == null || carAlreadyRegistered.size() == 0){
            car.setStatus(VehicleStatusEnum.ACTIVATED);
            car.setDeleted(Boolean.FALSE);
            return carPort.insert(car);
        }

        throw new CarAlreadyRegisteredException(car);
    }

    public Map<String, List<Car>> findAllGroupByManufacturer(CarFilter carFilter) {

        List<Car> carList = carPort.findAllWithOperatorOr(carFilter);

        if(carList != null && carList.size() > 0) {
            return groupByManufacturerThenSortByNameAndYear(carList);
        }

        throw new CarListNotFoundException();
    }

    public Car getById(Long id) {
        var car = carPort.getById(id);
        if(car != null) {
            return car;
        }
        throw new CarNotFoundException(id);
    }

    public void changeStatus(Long id, VehicleStatusEnum status) {
        Car car = getById(id);
        carPort.changeStatus(car, status);
    }

    public void delete(Long id) {
        Car car = getById(id);
        carPort.delete(car);
    }

    /**
     * A listagem de carros deverá ser agrupada por fabricante, e o agrupamento deverá ser
     * ordenado por nome e ano (do menor até o mais atual).
     *
     * @param carList
     * @return
     */
    private Map<String, List<Car>> groupByManufacturerThenSortByNameAndYear(List<Car> carList) {

        Comparator<Car> sortByNameAndYear = Comparator
                .comparing(Car::getName)
                .thenComparingLong(Car::getYear);

        return carList.stream()
                .collect(groupingBy(Car::getManufacturer,
                        mapping(Function.identity(), collectingAndThen(toList(),
                                e -> e.stream().sorted(sortByNameAndYear).collect(toList())))));
    }
}
