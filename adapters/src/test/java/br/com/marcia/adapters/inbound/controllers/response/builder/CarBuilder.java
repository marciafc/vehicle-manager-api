package br.com.marcia.adapters.inbound.controllers.response.builder;

import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.Builder;

@Builder
public class CarBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String chassi = "9BWZZZ377VT004251";

    @Builder.Default
    private String name = "Lamborghini Urus";

    @Builder.Default
    private String manufacturer = "Automobili Lamborghini S.P.A.";

    @Builder.Default
    private Long year = 2021L;

    @Builder.Default
    private String placa = "OPA0148";

    @Builder.Default
    private String color = "white";

    @Builder.Default
    private VehicleStatusEnum status = VehicleStatusEnum.ACTIVATED;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    public Car toCar() {
        return Car.builder()
                .id(id)
                .chassi(chassi)
                .name(name)
                .manufacturer(manufacturer)
                .year(year)
                .placa(placa)
                .color(color)
                .status(status)
                .deleted(deleted)
                .build();
    }
}
