package co.example.kafkatraining.consumer;

import co.example.kafkatraining.handler.InventoryHandler;
import co.example.kafkatraining.schemas.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemConsumer {
    private final InventoryHandler inventoryHandler;

    @KafkaListener(topics = "${app.topics.items}", id = "Items", containerFactory = "#{consumerConfigs.itemsListenerContainerFactory}")
    public void consumeItems(Item message) {
        log.info("Consuming item {}", message);
        inventoryHandler.create(message);
    }
}
