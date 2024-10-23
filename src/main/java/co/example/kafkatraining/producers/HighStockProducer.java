package co.example.kafkatraining.producers;

import co.example.kafkatraining.config.ApplicationProperties;
import co.example.kafkatraining.schemas.HighStock;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HighStockProducer {
    private final ApplicationProperties props;
    private final KafkaTemplate<String, HighStock> kafkaTemplate;

    public void send(HighStock message) {kafkaTemplate.send(props.getHighStockTopic(),message.id(), message);}
}
