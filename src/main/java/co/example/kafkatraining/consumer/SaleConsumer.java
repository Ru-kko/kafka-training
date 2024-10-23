package co.example.kafkatraining.consumer;

import co.example.kafkatraining.handler.SalesHandler;
import co.example.kafkatraining.schemas.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleConsumer {
    private final SalesHandler handler;

    @KafkaListener(id = "SALES", topics = "${app.topics.sales}", containerFactory = "#{consumerConfigs.salesListenerContainerFactory}")
    public void consume(Sale message) {
        handler.process(message);
    }
}
