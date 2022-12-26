package br.com.marcia.adapters.outbound.database.repository;

import br.com.marcia.adapters.outbound.database.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long>, JpaSpecificationExecutor<CarEntity> {

    CarEntity findByIdAndDeleted(Long id, Boolean deleted);
}
