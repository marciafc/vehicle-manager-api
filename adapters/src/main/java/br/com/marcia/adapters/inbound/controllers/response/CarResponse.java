package br.com.marcia.adapters.inbound.controllers.response;

import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Long id;

    private String chassi;

    private String name;

    private String manufacturer;

    private Integer year;

    private String color;

    private VehicleStatusEnum status;

    private String placa;
}
