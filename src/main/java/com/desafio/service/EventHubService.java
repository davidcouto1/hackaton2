package com.desafio.service;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventHubService {
    @Value("${eventhub.connection-string}")
    private String connectionString;

    public void enviarEvento(String mensagem) {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString)
                .buildProducerClient();
    producer.send(java.util.List.of(new EventData(mensagem)));
        producer.close();
    }
}
