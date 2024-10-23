package co.example.kafkatraining.consumer;

import co.example.kafkatraining.handler.InventoryHandler;
import co.example.kafkatraining.schemas.ItemMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemConsumer {
    private static final String ITEM_TOPIC = "ITEMS";
    private final InventoryHandler inventoryHandler;

    @KafkaListener(topics = ITEM_TOPIC, id = "Items", containerFactory = "#{consumerConfigs.itemsListenerContainerFactory}")
    public void consumeItems(ItemMessage message) {
        log.info("Consuming item {}", message);
        switch (message.action()) {
            case CREATE -> inventoryHandler.create(message.data());
            case DELETE -> inventoryHandler.delete(message.data().id());
        }
    }
}
