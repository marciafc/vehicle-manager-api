package br.com.marcia.adapters.inbound.controllers.request.builder;

import br.com.marcia.adapters.inbound.controllers.request.CarStatusRequest;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.Builder;

@Builder
public class CarStatusRequestBuilder {

    @Builder.Default
    private String status = VehicleStatusEnum.ACTIVATED.name();

    public CarStatusRequest toCarStatusRequest() {
        return CarStatusRequest.builder()
                .status(status)
                .build();
    }
}
