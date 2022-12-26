package br.com.marcia.application.core.usecase;

import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.exception.CarAlreadyRegisteredException;
import br.com.marcia.application.core.exception.CarNotFoundException;
import br.com.marcia.application.core.usecase.builder.CarBuilder;
import br.com.marcia.application.ports.out.CarPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarUserCaseTest {

    @Mock
    private CarPort carPort;

    @InjectMocks
    private CarUserCase carUserCase;

    @Test
    void whenCarInformedThenItShouldBeCreated() {

        Car expectedCar = CarBuilder.builder().build().toCar();

        when(carPort.findAllWithOperatorOr(any())).thenReturn(List.of());
        when(carPort.insert(any())).thenReturn(expectedCar);

        Car actualCar = carUserCase.insert(expectedCar);

        assertEquals(expectedCar, actualCar);
    }

    @Test
    void whenAlreadyRegisteredCarInformedThenAnExceptionShouldBeThrown() {

        Car expectedCar = CarBuilder.builder().build().toCar();
        when(carPort.findAllWithOperatorOr(any())).thenReturn(List.of(expectedCar));

        assertThrows(CarAlreadyRegisteredException.class, () -> carUserCase.insert(expectedCar));
    }

    @Test
    void whenValidCarIdIsGivenThenReturnACar() {

        Car expectedCar = CarBuilder.builder().build().toCar();
        when(carPort.getById(any())).thenReturn(expectedCar);

        Car actualCar = carUserCase.getById(expectedCar.getId());

        assertEquals(expectedCar, actualCar);
    }

    @Test
    void whenNotRegisteredCarIdIsGivenThenThrowAnException() {

        Car expectedCar = CarBuilder.builder().build().toCar();
        when(carPort.getById(any())).thenReturn(null);

        assertThrows(CarNotFoundException.class, () -> carUserCase.getById(expectedCar.getId()));
    }


    @Test
    void whenChangeStatusIsCalledThenUpdateToInformedStatus() {

        Car car = CarBuilder.builder().build().toCar();

        when(carPort.getById(any())).thenReturn(car);
        doNothing().when(carPort).changeStatus(any(), any());

        carUserCase.changeStatus(car.getId(), VehicleStatusEnum.RENTED);

        verify(carPort, times(1)).getById(car.getId());
        verify(carPort, times(1)).changeStatus(car, VehicleStatusEnum.RENTED);
    }

    @Test
    void whenChangeStatusIsCalledToNotRegisteredCarIdThenThrowAnException() {

        Car car = CarBuilder.builder().build().toCar();
        when(carPort.getById(any())).thenThrow(CarNotFoundException.class);

        assertThrows(CarNotFoundException.class, () ->
                carUserCase.changeStatus(Long.valueOf(1), VehicleStatusEnum.ACTIVATED));

        verify(carPort, times(1)).getById(Long.valueOf(1));
        verify(carPort, times(0)).changeStatus(car, VehicleStatusEnum.ACTIVATED);
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenACarShouldBeDeleted() {

        Car car = CarBuilder.builder().build().toCar();

        when(carPort.getById(any())).thenReturn(car);
        doNothing().when(carPort).delete(any());

        carUserCase.delete(car.getId());

        verify(carPort, times(1)).getById(car.getId());
        verify(carPort, times(1)).delete(car);
    }

    @Test
    void whenExclusionIsCalledToNotRegisteredCarThenThrowAnException() {

        Car car = CarBuilder.builder().build().toCar();
        when(carPort.getById(any())).thenThrow(CarNotFoundException.class);

        assertThrows(CarNotFoundException.class, () ->
                carUserCase.delete(Long.valueOf(1)));

        verify(carPort, times(1)).getById(Long.valueOf(1));
        verify(carPort, times(0)).delete(car);
    }
}
