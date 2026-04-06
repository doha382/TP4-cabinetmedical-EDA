package ma.fsr.eda.billingservice.config;

import ma.fsr.eda.billingservice.event.dto.FactureCreatedEvent;
import ma.fsr.eda.billingservice.event.dto.FactureFailedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, FactureCreatedEvent> factureCreatedProducerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean("factureCreatedKafkaTemplate")
    public KafkaTemplate<String, FactureCreatedEvent> factureCreatedKafkaTemplate(
            ProducerFactory<String, FactureCreatedEvent> factureCreatedProducerFactory) {
        return new KafkaTemplate<>(factureCreatedProducerFactory);
    }

    @Bean
    public ProducerFactory<String, FactureFailedEvent> factureFailedProducerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean("factureFailedKafkaTemplate")
    public KafkaTemplate<String, FactureFailedEvent> factureFailedKafkaTemplate(
            ProducerFactory<String, FactureFailedEvent> factureFailedProducerFactory) {
        return new KafkaTemplate<>(factureFailedProducerFactory);
    }
}
