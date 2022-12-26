package br.com.marcia.application.core.filters;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarFilter {

    private String chassi;

    private String name;

    private String manufacturer;

    private Long year;

    private String placa;
}
