package co.example.kafkatraining.handler;

import co.example.kafkatraining.config.ApplicationProperties;
import co.example.kafkatraining.jpa.entity.ItemEntity;
import co.example.kafkatraining.jpa.repository.ItemRepository;
import co.example.kafkatraining.producers.HighStockProducer;
import co.example.kafkatraining.schemas.HighStock;
import co.example.kafkatraining.schemas.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryHandler {
    private final ItemRepository itemRepository;
    private final ApplicationProperties props;
    private final HighStockProducer highStockProducer;


    public void create(Item item) {
        var saved = itemRepository.findById(item.id());

        var toSave = saved.orElse(new ItemEntity(item.id(), item.quantity()));

        if (saved.isPresent()) {
            toSave.incrementQuantity(toSave.getQuantity());
            log.info("Updating an existing item {}", item.id());
        }

        var result = this.itemRepository.save(toSave);

        if (result.getQuantity() > props.getHighStock()) {
            log.warn("item {} is exceeding the recommended stock, {}", result.getItemId(), result.getQuantity());
            HighStock message = HighStock.builder()
                    .id(result.getItemId())
                    .quantity(result.getQuantity())
                    .description("item is exceeding the recommended stock")
                    .gap(item.quantity() - props.getHighStock())
                    .build();
            highStockProducer.send(message);
        }
    }
}
