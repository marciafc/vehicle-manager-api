package br.com.marcia.adapters.inbound.controllers.response.builder;

import br.com.marcia.adapters.inbound.controllers.response.CarResponse;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.Builder;

@Builder
public class CarResponseBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String chassi = "9BWZZZ377VT004251";

    @Builder.Default
    private String name = "Lamborghini Urus";

    @Builder.Default
    private String manufacturer = "Automobili Lamborghini S.P.A.";

    @Builder.Default
    private Integer year = 2021;

    @Builder.Default
    private String placa = "OPA0148";

    @Builder.Default
    private String color = "white";

    @Builder.Default
    private VehicleStatusEnum status = VehicleStatusEnum.ACTIVATED;

    public CarResponse toCarResponse() {
        return CarResponse.builder()
                .id(id)
                .chassi(chassi)
                .name(name)
                .manufacturer(manufacturer)
                .year(year)
                .placa(placa)
                .color(color)
                .status(status)
                .build();
    }
}
