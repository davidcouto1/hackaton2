package com.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditoriaController {
    @Operation(
        summary = "Auditoria de operações",
        description = "Endpoint para consulta dos logs de auditoria das simulações. Retorna mensagem informando onde consultar os logs detalhados.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Mensagem de auditoria retornada",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'mensagem': 'Auditoria disponível via logs do sistema. Consulte o arquivo de log do serviço para detalhes.' }"))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
        }
    )
    @GetMapping("/api/auditoria")
    public Object consultarAuditoria() {
        // Exemplo: retorna logs do SimulacaoService (em produção, integrar com sistema de auditoria/logs)
        return java.util.Map.of("mensagem", "Auditoria disponível via logs do sistema. Consulte o arquivo de log do serviço para detalhes.");
    }
}
