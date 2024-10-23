package co.example.kafkatraining.config;

import co.example.kafkatraining.schemas.ItemMessage;
import co.example.kafkatraining.schemas.Sale;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfigs {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.listener.ack-mode}")
    private String ackMode;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    private Map<String, Object> consumerConfigs() {
        var res = new HashMap<String, Object>();

        res.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        res.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        res.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        return res;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Sale> salesListenerContainerFactory() {
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(Sale.class));

        var factory = new ConcurrentKafkaListenerContainerFactory<String, Sale>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.valueOf(ackMode.toUpperCase()));

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ItemMessage> itemsListenerContainerFactory() {
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(ItemMessage.class));

        var factory = new ConcurrentKafkaListenerContainerFactory<String, ItemMessage>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.valueOf(ackMode.toUpperCase()));

        return factory;
    }
}
