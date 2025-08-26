package com.desafio.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EventHubServiceTest {
    @MockBean
    private EventHubService eventHubService;

    @Test
    void testEnviarEvento() {
        var envelope = java.util.Map.of("idSimulacao", 123, "valorDesejado", 900.0);
        Mockito.doNothing().when(eventHubService).enviarEvento(envelope);
        eventHubService.enviarEvento(envelope);
        Mockito.verify(eventHubService, Mockito.times(1)).enviarEvento(envelope);
    }
}
