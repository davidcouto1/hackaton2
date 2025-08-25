
package com.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.desafio.model.Telemetria;
import com.desafio.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetria")
public class TelemetriaController {
    @Autowired
    private TelemetriaService telemetriaService;

    @Operation(summary = "Registrar telemetria", description = "Registra dados de telemetria da API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Telemetria registrada",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    @PostMapping
    public Telemetria criarTelemetria(@org.springframework.web.bind.annotation.RequestBody Telemetria telemetria) {
        return telemetriaService.salvarTelemetria(telemetria);
    }

    @Operation(summary = "Listar telemetria", description = "Retorna envelope agrupado de telemetria por data.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de telemetria",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    @GetMapping
    public Object listarTelemetria(@RequestParam(value = "data", required = false) String data) {
        List<Telemetria> todas = telemetriaService.listarTelemetria();
        List<Telemetria> filtradas = todas;
        if (data != null) {
            filtradas = todas.stream().filter(t -> t.getDataReferencia().toString().equals(data)).toList();
        }
        List<Object> listaEndpoints = new java.util.ArrayList<>();
        for (Telemetria t : filtradas) {
            listaEndpoints.add(java.util.Map.of(
                "nomeApi", t.getNomeApi(),
                "qtdRequisicoes", t.getQtdRequisicoes(),
                "tempoMedio", t.getTempoMedio(),
                "tempoMinimo", t.getTempoMinimo(),
                "tempoMaximo", t.getTempoMaximo(),
                "percentualSucesso", t.getPercentualSucesso()
            ));
        }
        String dataReferencia = filtradas.isEmpty() ? null : filtradas.get(0).getDataReferencia().toString();
        return java.util.Map.of(
            "dataReferencia", dataReferencia,
            "listaEndpoints", listaEndpoints
        );
    }
}
