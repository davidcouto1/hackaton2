
package com.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.desafio.model.Simulacao;
import com.desafio.repository.SimulacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/por-produto-dia")
    public Map<String, Object> relatorioPorProdutoDia(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Simulacao> simulacoes = simulacaoRepository.findAll().stream()
                .filter(s -> s.getDataSimulacao() != null && s.getDataSimulacao().toLocalDate().equals(data))
                .collect(Collectors.toList());
        Map<Integer, List<Simulacao>> agrupado = simulacoes.stream().collect(Collectors.groupingBy(Simulacao::getCodigoProduto));
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<Integer, List<Simulacao>> entry : agrupado.entrySet()) {
            List<Simulacao> lista = entry.getValue();
            BigDecimal taxaMediaJuro = lista.stream().map(Simulacao::getTaxaJuros).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(lista.size()), 4, RoundingMode.HALF_UP);
            BigDecimal valorMedioPrestacao = lista.stream().map(Simulacao::getValorTotalSac).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(lista.size()), 2, RoundingMode.HALF_UP);
            BigDecimal valorTotalDesejado = lista.stream().map(Simulacao::getValorDesejado).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal valorTotalCredito = lista.stream().map(Simulacao::getValorTotalSac).reduce(BigDecimal.ZERO, BigDecimal::add);
            resultado.add(Map.of(
                "codigoProduto", entry.getKey(),
                "descricaoProduto", lista.get(0).getNomeProduto(),
                "taxaMediaJuro", taxaMediaJuro,
                "valorMedioPrestacao", valorMedioPrestacao,
                "valorTotalDesejado", valorTotalDesejado,
                "valorTotalCredito", valorTotalCredito
            ));
        }
        return Map.of("dataReferencia", data, "simulacoes", resultado);
    }
}
