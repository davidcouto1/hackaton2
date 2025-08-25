package com.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TelemetriaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void deveListarTelemetriaComSucesso() throws Exception {
    mockMvc.perform(get("/api/telemetria"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataReferencia").exists())
        .andExpect(jsonPath("$.listaEndpoints").isArray());
    }

    @Test
    void deveListarTelemetriaSemDados() throws Exception {
    mockMvc.perform(get("/api/telemetria"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.listaEndpoints").isArray());
    }

    @Test
    void deveListarTelemetriaComDados() throws Exception {
        // Cria telemetria
        var telemetria = new com.desafio.model.Telemetria();
        telemetria.setDataReferencia(java.time.LocalDate.now());
        telemetria.setNomeApi("/api/teste");
        telemetria.setTempoRespostaMs(100);
        telemetria.setStatusHttp(200);
        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/telemetria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(telemetria))
        ).andExpect(status().isOk());

        // Consulta telemetria
    mockMvc.perform(get("/api/telemetria"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataReferencia").exists())
        .andExpect(jsonPath("$.listaEndpoints").isArray());
    }
}
