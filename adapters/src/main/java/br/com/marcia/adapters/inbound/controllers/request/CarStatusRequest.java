package br.com.marcia.adapters.inbound.controllers.request;

import br.com.marcia.adapters.validators.EnumValidator;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarStatusRequest {

    @EnumValidator(
            enumClazz = VehicleStatusEnum.class,
            message = "O status informado é inválido. Os status aceitos são ACTIVATED, DEACTIVATED, RESERVED, RENTED")
    private String status;
}
