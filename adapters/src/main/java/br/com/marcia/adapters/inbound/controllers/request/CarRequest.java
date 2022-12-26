package br.com.marcia.adapters.inbound.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @NotEmpty
    private String chassi;

    @NotEmpty
    private String name;

    @NotEmpty
    private String manufacturer;

    @NotNull
    private Long year;

    @NotEmpty
    private String color;

    @NotEmpty
    private String placa;
}
