package br.com.marcia.adapters.inbound.controllers.mapper;

import br.com.marcia.adapters.inbound.controllers.request.CarRequest;
import br.com.marcia.adapters.inbound.controllers.response.CarResponse;
import br.com.marcia.application.core.domain.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    Car toModel(CarRequest carRequest);

    CarResponse toResponse(Car car);

    Map<String, List<CarResponse>> toResponse(Map<String, List<Car>> cars);

    List<CarResponse> toListCarResponse(List<Car> value);
}
