package br.com.marcia.adapters.inbound.controllers.request.builder;

import br.com.marcia.adapters.inbound.controllers.request.CarRequest;
import lombok.Builder;

@Builder
public class CarRequestBuilder {

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

    public CarRequest toCarRequest() {
        return CarRequest.builder()
                .chassi(chassi)
                .name(name)
                .manufacturer(manufacturer)
                .year(year)
                .placa(placa)
                .color(color)
                .build();
    }
}
