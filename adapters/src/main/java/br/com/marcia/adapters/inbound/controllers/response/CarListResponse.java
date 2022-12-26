package br.com.marcia.adapters.inbound.controllers.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarListResponse {

    private Map<String, List<CarResponse>> cars;
}
