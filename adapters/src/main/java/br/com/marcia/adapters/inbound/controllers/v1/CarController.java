package br.com.marcia.adapters.inbound.controllers.v1;

import br.com.marcia.adapters.inbound.controllers.mapper.CarMapper;
import br.com.marcia.adapters.inbound.controllers.request.CarRequest;
import br.com.marcia.adapters.inbound.controllers.request.CarStatusRequest;
import br.com.marcia.adapters.inbound.controllers.response.CarResponse;
import br.com.marcia.adapters.inbound.controllers.v1.openapi.CarControllerOpenApi;
import br.com.marcia.application.core.domain.Car;
import br.com.marcia.application.core.enums.VehicleStatusEnum;
import br.com.marcia.application.core.filters.CarFilter;
import br.com.marcia.application.ports.in.CarUseCasePort;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class CarController implements CarControllerOpenApi {

    @NonNull
    private final CarUseCasePort carUseCasePort;

    private final CarMapper carMapper = CarMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<CarResponse> insert(@Valid @RequestBody CarRequest carRequest) {

        log.info("Inserindo carro com chassi %s e placa %s", carRequest.getChassi(), carRequest.getPlaca());

        var car = carMapper.toModel(carRequest);
        Car insertedCar = carUseCasePort.insert(car);

        return ResponseEntity.status(HttpStatus.CREATED).body(carMapper.toResponse(insertedCar));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<CarResponse>>> findAll(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String manufacturer,
                                  @RequestParam(required = false) Long year) {

        log.info("Listando carros filtrados por nome ou fabricante ou ano");

        var carFilter = CarFilter.builder().name(name)
                .manufacturer(manufacturer)
                .year(year)
                .build();

        Map<String, List<CarResponse>> cars = carMapper.toResponse(carUseCasePort.findAllGroupByManufacturer(carFilter));
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> find(@PathVariable("id") Long id) {

        log.info("Buscando carro com id %s", id);

        return ResponseEntity.status(HttpStatus.OK).body(
                carMapper.toResponse(carUseCasePort.getById(id)));
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") Long id,
                                            @Valid @RequestBody CarStatusRequest carStatusRequest) {

        log.info("Alterando o status do carro com id %s para %s", id, carStatusRequest.getStatus());

        carUseCasePort.changeStatus(id, VehicleStatusEnum.valueOf(carStatusRequest.getStatus()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        log.info("Deletando o carro com id %s ", id);

        carUseCasePort.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
