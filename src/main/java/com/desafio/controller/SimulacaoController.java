package com.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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


    @Operation(summary = "Criar simulação de crédito", description = "Realiza a simulação de crédito e retorna envelope agrupado por tipo de amortização.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: valor negativo, prazo inválido)",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'erro': 'Valor desejado deve ser positivo.' }"))),
        @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'erro': 'Não autorizado.' }"))),
        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'erro': 'Acesso proibido.' }"))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'erro': 'Recurso não encontrado.' }"))),
        @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'erro': 'Erro interno.' }")))
    })
    @PostMapping
    public ResponseEntity<?> criarSimulacao(@org.springframework.web.bind.annotation.RequestBody Simulacao simulacao) {
        if (simulacao.getValorDesejado() == null || simulacao.getValorDesejado().signum() <= 0) {
            return ResponseEntity.badRequest().body(java.util.Map.of("erro", "Valor desejado deve ser positivo."));
        }
        if (simulacao.getPrazo() < 1) {
            return ResponseEntity.badRequest().body(java.util.Map.of("erro", "Prazo deve ser maior ou igual a 1."));
        }
        try {
            Simulacao resultado = simulacaoService.salvarSimulacao(simulacao);
            var envelope = simulacaoService.montarEnvelopeSimulacao(resultado);
            return ResponseEntity.ok(envelope);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Nenhum produto válido encontrado")) {
                return ResponseEntity.status(400)
                    .header("Content-Type", "application/json")
                    .body(java.util.Map.of(
                        "erro", "Parâmetros fora dos limites permitidos para os produtos. Verifique valor e prazo.",
                        "codigo", 400,
                        "detalhe", "O valor ou prazo informado está fora dos limites permitidos para os produtos disponíveis. Consulte as regras e tente novamente."
                    ));
            }
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json")
                .body(java.util.Map.of(
                    "erro", "Erro interno na simulação.",
                    "codigo", 500,
                    "detalhe", msg != null ? msg : "Erro inesperado."
                ));
        }
    }

    @Operation(summary = "Listar simulações", description = "Retorna envelope paginado das simulações realizadas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de simulações",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> listarSimulacoes(@RequestParam(defaultValue = "1") int pagina,
                          @RequestParam(defaultValue = "200") int qtdRegistrosPagina) {
    Map<String, Object> envelope = simulacaoService.listarSimulacoesPaginadas(pagina, qtdRegistrosPagina);
    return ResponseEntity.ok(envelope);
    }
}
