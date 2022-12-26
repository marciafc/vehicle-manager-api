package br.com.marcia.adapters.inbound.controllers.v1.openapi;

import br.com.marcia.adapters.inbound.controllers.request.CarRequest;
import br.com.marcia.adapters.inbound.controllers.request.CarStatusRequest;
import br.com.marcia.adapters.inbound.controllers.response.CarResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Map;

@Tag(name = "Cars")
public interface CarControllerOpenApi {

    @Operation(summary = "Cadastrar um carro", responses = {
            @ApiResponse(responseCode = "201", description = "Carro cadastrado"),
            @ApiResponse(responseCode = "400", description = "Carro já cadastrado com o chassi ou a placa informados")
    })
    ResponseEntity<CarResponse> insert(
            @RequestBody(description = "Representação de um novo carro", required = true) CarRequest carRequest);

    @Operation(summary = "Listar os carros agrupados por fabricante, trazendo cada agrupamento ordenado pelo nome e ano", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Nenhum carro foi encontrado")
    })
    ResponseEntity<Map<String, List<CarResponse>>> findAll(@Parameter(required = false) String name,
                                                           @Parameter(required = false) String manufacturer,
                                                           @Parameter(required = false) Long year);

    @Operation(summary = "Buscar um carro por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    })
    ResponseEntity<CarResponse> find(
            @Parameter(description = "ID de um carro", example = "1", required = true) Long id);

    @Operation(summary = "Atualizar o status de um carro", responses = {
            @ApiResponse(responseCode = "204", description = "Status atualizado"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado"),
    })
    ResponseEntity<Void> changeStatus(
            @Parameter(description = "ID de um carro", example = "1", required = true) Long id,
            @RequestBody(description = "Representação do status do carro", required = true) CarStatusRequest carStatusRequest);

    @Operation(summary = "Excluir um carro por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Carro excluído"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID de um carro", example = "1", required = true) Long id);

}
