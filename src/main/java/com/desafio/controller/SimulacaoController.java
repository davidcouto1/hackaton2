package com.desafio.controller;

import com.desafio.model.Simulacao;
import com.desafio.model.Parcela;
import com.desafio.service.SimulacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/simulacoes")
public class SimulacaoController {
    @Autowired
    private SimulacaoService simulacaoService;


    @PostMapping
    public ResponseEntity<?> criarSimulacao(@RequestBody Simulacao simulacao) {
        Simulacao resultado = simulacaoService.salvarSimulacao(simulacao);
        // Agrupar parcelas por tipo
        var sacParcelas = resultado.getParcelas().stream().filter(p -> "SAC".equals(p.getTipo())).toList();
        var priceParcelas = resultado.getParcelas().stream().filter(p -> "PRICE".equals(p.getTipo())).toList();
        var envelope = new java.util.LinkedHashMap<String, Object>();
        envelope.put("idSimulacao", resultado.getIdSimulacao());
        envelope.put("codigoProduto", resultado.getCodigoProduto());
        envelope.put("nomeProduto", resultado.getNomeProduto());
        envelope.put("taxaJuros", resultado.getTaxaJuros());
        java.util.List<java.util.Map<String, Object>> resultadosSimulacao = new java.util.ArrayList<>();
        java.util.Map<String, Object> sac = new java.util.LinkedHashMap<>();
        sac.put("tipo", "SAC");
        sac.put("parcelas", sacParcelas.stream().map(p -> java.util.Map.of(
            "numero", p.getNumero(),
            "valorAmortizacao", p.getValorAmortizacao(),
            "valorJuros", p.getValorJuros(),
            "valorPrestacao", p.getValorPrestacao()
        )).toList());
        resultadosSimulacao.add(sac);
        java.util.Map<String, Object> price = new java.util.LinkedHashMap<>();
        price.put("tipo", "PRICE");
        price.put("parcelas", priceParcelas.stream().map(p -> java.util.Map.of(
            "numero", p.getNumero(),
            "valorAmortizacao", p.getValorAmortizacao(),
            "valorJuros", p.getValorJuros(),
            "valorPrestacao", p.getValorPrestacao()
        )).toList());
        resultadosSimulacao.add(price);
        envelope.put("resultadosSimulacao", resultadosSimulacao);
        return ResponseEntity.ok(envelope);
    }

    @GetMapping
    public ResponseEntity<?> listarSimulacoes(@RequestParam(defaultValue = "1") int pagina,
                          @RequestParam(defaultValue = "200") int qtdRegistrosPagina) {
    List<Simulacao> todas = simulacaoService.listarSimulacoes();
    int total = todas.size();
    int inicio = (pagina - 1) * qtdRegistrosPagina;
    int fim = Math.min(inicio + qtdRegistrosPagina, total);
    List<Simulacao> registros = inicio < total ? todas.subList(inicio, fim) : List.of();
        List<Map<String, Object>> registrosFormatados = new java.util.ArrayList<>();
        for (Simulacao s : registros) {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("idSimulacao", s.getIdSimulacao());
            map.put("valorDesejado", s.getValorDesejado());
            map.put("prazo", s.getPrazo());
            map.put("valorTotalSac", s.getValorTotalSac());
            map.put("valorTotalPrice", s.getValorTotalPrice());
            registrosFormatados.add(map);
        }
    Map<String, Object> envelope = Map.of(
        "pagina", pagina,
        "qtdRegistros", total,
        "qtdRegistrosPagina", qtdRegistrosPagina,
        "registros", registrosFormatados
    );
    return ResponseEntity.ok(envelope);
    }
}
