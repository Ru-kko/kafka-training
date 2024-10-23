package co.example.kafkatraining.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    @Value("${app.topics.items}")
    private String itemsTopic;
    @Value("${app.topics.sales}")
    private String salesTopic;
    @Value("${app.topics.high-stock}")
    private String highStockTopic;
    @Value("${app.topics.refund}")
    private String refundTopic;
    @Value("${app.topics.insufficient-stock}")
    private String insufficientStockTopic;
    @Value("${app.topics.low-stock}")
    private String lowStockTopic;
}
