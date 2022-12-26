package br.com.marcia.adapters.outbound.database.adapter;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import br.com.marcia.adapters.outbound.database.mapper.CarMapper;
import br.com.marcia.adapters.outbound.database.repository.CarRepository;
import br.com.marcia.adapters.outbound.database.specifications.CarSpecifications;
import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.filters.CarFilter;
import br.com.marcia.application.ports.out.CarPort;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.*;

@Component
@AllArgsConstructor
public class CarAdapter implements CarPort {

    @NonNull
    private final CarRepository carRepository;

    private final CarMapper carMapper = CarMapper.INSTANCE;

    @Override
    public Car insert(Car car) {
        var carEntity = carMapper.toEntity(car);
        return carMapper.toModel(carRepository.save(carEntity));
    }

    @Override
    public List<Car> findAllWithOperatorOr(CarFilter carFilter) {

        return carRepository.findAll(where(CarSpecifications.findAllWithOperatorOr(carFilter)))
                    .stream()
                    .map(carMapper::toModel)
                    .collect(Collectors.toList());
    }

    @Override
    public Car getById(Long id) {
        return carMapper.toModel(carRepository.findByIdAndDeleted(id, Boolean.FALSE));
    }

    @Override
    public void changeStatus(Car car, VehicleStatusEnum status) {
        CarEntity carEntity = carMapper.toEntity(car);
        carEntity.setStatus(status);
        carRepository.save(carEntity);

    }

    @Override
    public void delete(Car car) {
        CarEntity carEntity = carMapper.toEntity(car);
        carEntity.setDeleted(Boolean.TRUE);
        carRepository.save(carEntity);
    }
}
