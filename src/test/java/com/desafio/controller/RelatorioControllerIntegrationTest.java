package com.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class RelatorioControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void deveRetornarRelatorioVazioQuandoNaoHaSimulacoes() throws Exception {
        mockMvc.perform(get("/api/relatorio/por-produto-dia?data=" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataReferencia").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.simulacoes").isArray());
    }

    @Test
    void deveRetornarRelatorioComSimulacao() throws Exception {
        // Cria simulação
        Map<String, Object> request = Map.of(
            "valorDesejado", new BigDecimal("900.00"),
            "prazo", 5
        );
        mockMvc.perform(post("/api/simulacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Consulta relatório
        mockMvc.perform(get("/api/relatorio/por-produto-dia?data=" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataReferencia").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.simulacoes").isArray());
    }
}
