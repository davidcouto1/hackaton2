package com.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SimulacaoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarEnvelopeSimulacaoCorreto() throws Exception {
        Map<String, Object> request = Map.of(
            "valorDesejado", new BigDecimal("900.00"),
            "prazo", 5
        );
        mockMvc.perform(post("/api/simulacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoProduto").exists())
                .andExpect(jsonPath("$.resultadosSimulacao").isArray())
                .andExpect(jsonPath("$.resultadosSimulacao[0].tipo").value("SAC"))
                .andExpect(jsonPath("$.resultadosSimulacao[1].tipo").value("PRICE"));
    }
}
