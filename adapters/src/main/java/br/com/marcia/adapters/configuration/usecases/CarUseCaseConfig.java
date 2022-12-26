package br.com.marcia.adapters.configuration.usecases;

import br.com.marcia.adapters.outbound.database.adapter.CarAdapter;
import br.com.marcia.application.core.usecase.CarUserCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarUseCaseConfig {

    @Bean
    public CarUserCase carUseCase(CarAdapter carAdapter){
        return new CarUserCase(carAdapter);
    }
}
