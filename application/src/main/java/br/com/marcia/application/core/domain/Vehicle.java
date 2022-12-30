package br.com.marcia.application.core.domain;

import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
abstract class Vehicle {

    private Long id;

    private String chassi;

    private String name;

    private String manufacturer;

    private Integer year;

    private String color;

    private VehicleStatusEnum status;

    private String placa;

    private boolean deleted;
}
