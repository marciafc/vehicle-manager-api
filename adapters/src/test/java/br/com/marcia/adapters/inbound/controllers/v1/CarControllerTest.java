package br.com.marcia.adapters.inbound.controllers.v1;

import br.com.marcia.adapters.inbound.controllers.ApiExceptionHandler;
import br.com.marcia.adapters.inbound.controllers.request.CarRequest;
import br.com.marcia.adapters.inbound.controllers.request.CarStatusRequest;
import br.com.marcia.adapters.inbound.controllers.request.builder.CarRequestBuilder;
import br.com.marcia.adapters.inbound.controllers.request.builder.CarStatusRequestBuilder;
import br.com.marcia.adapters.inbound.controllers.response.CarResponse;
import br.com.marcia.adapters.inbound.controllers.response.builder.CarBuilder;
import br.com.marcia.adapters.inbound.controllers.response.builder.CarResponseBuilder;
import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.exception.CarAlreadyRegisteredException;
import br.com.marcia.application.core.exception.CarNotFoundException;
import br.com.marcia.application.ports.in.CarUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static br.com.marcia.adapters.inbound.controllers.utils.JsonConvertionUtils.asJsonString;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    private static final String CAR_API_URL_PATH = "/v1/cars";
    private static final String CAR_CHANGE_STATUS = "/change-status";

    private MockMvc mockMvc;

    @Mock
    private CarUseCasePort carUseCasePort;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenACarIsCreated() throws Exception {

        CarResponse carResponse = CarResponseBuilder.builder().build().toCarResponse();
        CarRequest carRequest = CarRequestBuilder.builder().build().toCarRequest();

        Car car = CarBuilder.builder().build().toCar();

        when(carUseCasePort.insert(any())).thenReturn(car);

        mockMvc.perform(post(CAR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(carResponse.getId().intValue())))
                .andExpect(jsonPath("$.name", is(carResponse.getName())))
                .andExpect(jsonPath("$.placa", is(carResponse.getPlaca())))
                .andExpect(jsonPath("$.chassi", is(carResponse.getChassi())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {

        CarRequest carRequest = CarRequestBuilder.builder().build().toCarRequest();
        carRequest.setChassi("");

        mockMvc.perform(post(CAR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPOSTIsCalledWithCarAlreadyRegisteredThenBadRequestStatusIsReturned() throws Exception {

        CarRequest carRequest = CarRequestBuilder.builder().build().toCarRequest();

        when(carUseCasePort.insert(any())).thenThrow(CarAlreadyRegisteredException.class);

        mockMvc.perform(post(CAR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {

        CarResponse carResponse = CarResponseBuilder.builder().build().toCarResponse();
        Car car = CarBuilder.builder().build().toCar();

        when(carUseCasePort.getById(any())).thenReturn(car);

        mockMvc.perform(get(CAR_API_URL_PATH + "/" + car.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carResponse.getId().intValue())))
                .andExpect(jsonPath("$.name", is(carResponse.getName())))
                .andExpect(jsonPath("$.placa", is(carResponse.getPlaca())))
                .andExpect(jsonPath("$.chassi", is(carResponse.getChassi())));
    }

    @Test
    void whenGETIsCalledButNotRegisteredCarThenNotFoundStatusIsReturned() throws Exception {

        when(carUseCasePort.getById(any())).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get(CAR_API_URL_PATH + "/" + Long.valueOf(1L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPATCHIsCalledToChangeStatusThenOKStatusIsReturned() throws Exception {

        CarStatusRequest carStatusRequest = CarStatusRequestBuilder.builder().build().toCarStatusRequest();
        doNothing().when(carUseCasePort).changeStatus(any(), any());

        mockMvc.perform(patch(CAR_API_URL_PATH + "/" + Long.valueOf(1L) + CAR_CHANGE_STATUS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carStatusRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenPATCHIsCalledToChangeStatusToNotRegisteredCarThenNotFoundStatusIsReturned() throws Exception {

        CarStatusRequest carStatusRequest = CarStatusRequestBuilder.builder().build().toCarStatusRequest();
        doThrow(new CarNotFoundException(Long.valueOf(1))).when(carUseCasePort).changeStatus(any(), any());

        mockMvc.perform(patch(CAR_API_URL_PATH + "/" + Long.valueOf(1L) + CAR_CHANGE_STATUS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carStatusRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {

        Long id = Long.valueOf(1);
        doNothing().when(carUseCasePort).delete(id);

        mockMvc.perform(delete(CAR_API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {

        Long id = Long.valueOf(1);
        doThrow(CarNotFoundException.class).when(carUseCasePort).delete(id);

        mockMvc.perform(delete(CAR_API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
