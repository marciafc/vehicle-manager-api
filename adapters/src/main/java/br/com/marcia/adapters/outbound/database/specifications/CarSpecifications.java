package br.com.marcia.adapters.outbound.database.specifications;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import br.com.marcia.application.core.filters.CarFilter;
import org.springframework.data.jpa.domain.Specification;

public final class CarSpecifications {

    private CarSpecifications() {
    }

    public static Specification<CarEntity> findAllWithOperatorOr(CarFilter carFilter) {
        return new CarListSpecification(carFilter);
    }

}
