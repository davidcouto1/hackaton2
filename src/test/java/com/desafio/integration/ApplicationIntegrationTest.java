package com.desafio.integration;

import com.desafio.model.Telemetria;
import com.desafio.model.Simulacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fluxoCompletoSimulacaoETelemetria() throws Exception {
        // 1. Simular crédito
        Simulacao simulacao = new Simulacao();
        simulacao.setValorDesejado(new BigDecimal("900.00"));
        simulacao.setPrazo(5);
        simulacao.setCodigoProduto(1);
        simulacao.setNomeProduto("Produto 1");
        simulacao.setTaxaJuros(new BigDecimal("0.0178990000"));
        simulacao.setValorTotalSac(new BigDecimal("1243.28"));
        simulacao.setValorTotalPrice(new BigDecimal("1243.28"));
        String simJson = objectMapper.writeValueAsString(simulacao);
        mockMvc.perform(post("/api/simulacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(simJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoProduto").value(1));

        // 2. Listar simulações
        mockMvc.perform(get("/api/simulacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registros").isArray());

        // 3. Listar produtos
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").exists());

        // 4. Relatório por produto e dia
        mockMvc.perform(get("/api/relatorio/por-produto-dia")
                .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.simulacoes").isArray());

        // 5. Telemetria por data
        mockMvc.perform(get("/api/telemetria")
                .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listaEndpoints").isArray());
    }
}
