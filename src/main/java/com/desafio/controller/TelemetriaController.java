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
    @GetMapping("/todos")
    public List<Telemetria> listarTodosRegistrosTelemetria() {
        return telemetriaService.listarTelemetria();
    }
    @PostMapping("/teste")
    public Telemetria criarTelemetriaTeste() {
        Telemetria telemetria = new Telemetria();
        telemetria.setDataReferencia(java.time.LocalDate.now());
        telemetria.setNomeApi("/api/teste");
        telemetria.setTempoRespostaMs(123);
        telemetria.setStatusHttp(200);
        return telemetriaService.salvarTelemetria(telemetria);
    }
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
        java.time.LocalDate dataRef = null;
        if (data != null) {
            dataRef = java.time.LocalDate.parse(data);
        }
        var listaEndpoints = dataRef != null ? telemetriaService.buscarPorData(dataRef) : telemetriaService.buscarTodosAgrupados();
        java.util.Map<String, Object> envelope = new java.util.HashMap<>();
        // Preenche dataReferencia corretamente
        if (dataRef != null) {
            envelope.put("dataReferencia", dataRef);
        } else if (!listaEndpoints.isEmpty() && listaEndpoints.get(0) instanceof com.desafio.dto.TelemetriaDTO) {
            // Se houver dados, tenta pegar a data do primeiro DTO (se existir)
            // envelope.put("dataReferencia", ...); // Se DTO tiver campo de data
            envelope.put("dataReferencia", java.time.LocalDate.now());
        } else {
            envelope.put("dataReferencia", java.time.LocalDate.now());
        }
        envelope.put("endpoints", listaEndpoints);
        return envelope;
    }
}
