package br.com.marcia.adapters.outbound.database.entity;

import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "Vehicle")
@Table(name = "Vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length=50)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String chassi;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String manufacturer;

    @Column(name = "year_manufacture", nullable = false)
    private Long year;

    @Column(nullable = false, length = 100)
    private String color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatusEnum status;

    @Column(nullable = false, length = 50)
    private String placa;

    @Column(nullable = false)
    private Boolean deleted;
}
