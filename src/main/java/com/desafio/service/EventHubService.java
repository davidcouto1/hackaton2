package com.desafio.service;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventHubService {
    @Value("${eventhub.connection-string}")
    private String connectionString;
    @Value("${eventhub.name}")
    private String eventHubName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void enviarEvento(Object envelope) {
        EventHubProducerClient producer = new EventHubClientBuilder()
            .connectionString(connectionString, eventHubName)
            .buildProducerClient();
        try {
            String json = objectMapper.writeValueAsString(envelope);
            EventDataBatch batch = producer.createBatch();
            batch.tryAdd(new EventData(json));
            producer.send(batch);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar evento para EventHub", e);
        } finally {
            producer.close();
        }
    }
}
