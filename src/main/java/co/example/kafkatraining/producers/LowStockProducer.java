package co.example.kafkatraining.producers;

import co.example.kafkatraining.config.ApplicationProperties;
import co.example.kafkatraining.schemas.LowStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LowStockProducer {
    private final ApplicationProperties props;
    private final KafkaTemplate<String, LowStock> kafkaTemplate;

    public void send(LowStock message) {
        kafkaTemplate.send(props.getLowStockTopic(), message.id(), message);
    }
}
