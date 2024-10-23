package co.example.kafkatraining.schemas;

public record ItemMessage(Item data, InventoryAction action) {
    public enum InventoryAction { CREATE, DELETE }
}
