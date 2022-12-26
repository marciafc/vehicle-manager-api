package br.com.marcia.adapters.outbound.database.mapper;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import br.com.marcia.application.core.domain.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    Car toModel(CarEntity carEntity);

    CarEntity toEntity(Car car);
}
