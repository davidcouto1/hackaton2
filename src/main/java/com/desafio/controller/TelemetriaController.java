package com.desafio.controller;

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

    @PostMapping
    public Telemetria criarTelemetria(@RequestBody Telemetria telemetria) {
        return telemetriaService.salvarTelemetria(telemetria);
    }

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
