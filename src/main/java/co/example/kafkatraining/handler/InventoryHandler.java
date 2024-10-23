package co.example.kafkatraining.handler;

import co.example.kafkatraining.jpa.entity.ItemEntity;
import co.example.kafkatraining.jpa.repository.ItemRepository;
import co.example.kafkatraining.schemas.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryHandler {
    private final ItemRepository itemRepository;

    public ItemEntity create(Item item) {
        var toSave = new ItemEntity(item.id(), item.quantity());
        return itemRepository.save(toSave);
    }

    public void  delete(String id) {
        if (id == null) {
            log.warn("Cannot delete null item");
            return;
        }

        itemRepository.deleteById(id);
    }
}
