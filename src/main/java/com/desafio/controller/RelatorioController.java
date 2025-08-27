
package com.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.desafio.model.Simulacao;
import com.desafio.repository.SimulacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {
    @Autowired
    private SimulacaoRepository simulacaoRepository;

    @Operation(summary = "Relatório por produto e dia", description = "Retorna envelope de relatório agrupado por produto e data.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    @Cacheable("relatorioPorProdutoDia")
    @GetMapping("/por-produto-dia")
    public ResponseEntity<?> relatorioPorProdutoDia(@RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        if (data == null) {
            return ResponseEntity.status(400)
                .header("Content-Type", "application/json")
                .body(Map.of(
                    "erro", "Selecione uma data.",
                    "codigo", 400,
                    "detalhe", "O parâmetro 'data' é obrigatório para consultar o relatório por produto e dia."
                ));
        }
        var resultado = simulacaoRepository.buscarPorProdutoEDia(data);
        if (resultado == null || resultado.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "dataReferencia", data,
                "simulacoes", Collections.emptyList(),
                "mensagem", "Nenhum registro encontrado para a data selecionada."
            ));
        }
        return ResponseEntity.ok(Map.of("dataReferencia", data, "simulacoes", resultado));
    }
}
