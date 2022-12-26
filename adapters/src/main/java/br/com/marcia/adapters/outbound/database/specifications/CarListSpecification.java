package br.com.marcia.adapters.outbound.database.specifications;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import br.com.marcia.adapters.outbound.database.entity.CarEntity_;
import br.com.marcia.application.core.filters.CarFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CarListSpecification implements Specification<CarEntity> {

    private final CarFilter carFilter;

    @Override
    public Predicate toPredicate(Root<CarEntity> carEntity,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        Predicate whereDynamic = prepareWhereWithDynamicFilters(criteriaBuilder, carEntity);
        Predicate whereRequired = prepareWhereWithRequiredFilters(criteriaBuilder, carEntity);

        if(whereDynamic.getExpressions() != null && whereDynamic.getExpressions().size() >= 1) {
            return executeQuery(whereDynamic, whereRequired, criteriaBuilder);
        }
        return executeQuery(whereRequired, criteriaBuilder);
    }

    private Predicate executeQuery(Predicate predicateRequired,
                                   CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.and(predicateRequired);
    }

    private Predicate executeQuery(Predicate predicateDynamic,
                                   Predicate predicateRequired,
                                   CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.and(predicateDynamic, predicateRequired);
    }

    private Predicate prepareWhereWithDynamicFilters(CriteriaBuilder criteriaBuilder, Root<CarEntity> carEntity) {
        List<Predicate> predicates = new ArrayList<>();

        // name
        if(StringUtils.isNotEmpty(carFilter.getName())){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(carEntity.get(CarEntity_.name)),
                    "%" + carFilter.getName().toLowerCase() + "%"));
        }

        // manufacturer
        if(StringUtils.isNotEmpty(carFilter.getManufacturer())){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(carEntity.get(CarEntity_.manufacturer)),
                    "%" + carFilter.getManufacturer().toLowerCase() + "%"));
        }

        // year
        if(carFilter.getYear() != null) {
            predicates.add(criteriaBuilder.equal(carEntity.get(CarEntity_.year), carFilter.getYear()));
        }

        // chassi
        if(StringUtils.isNotEmpty(carFilter.getChassi())){
            predicates.add(criteriaBuilder.equal(carEntity.get(CarEntity_.chassi), carFilter.getChassi()));
        }

        // placa
        if(StringUtils.isNotEmpty(carFilter.getPlaca())){
            predicates.add(criteriaBuilder.equal(carEntity.get(CarEntity_.placa), carFilter.getPlaca()));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate prepareWhereWithRequiredFilters(CriteriaBuilder criteriaBuilder, Root<CarEntity> carEntity) {
        return criteriaBuilder.isFalse(carEntity.get(CarEntity_.deleted));
    }
}
