package co.example.kafkatraining.schemas;

import lombok.Builder;

@Builder
public record HighStock(String id,Integer quantity ,Integer gap, String description) {
}
