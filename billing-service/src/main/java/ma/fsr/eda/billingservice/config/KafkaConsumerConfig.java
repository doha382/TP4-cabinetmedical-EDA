package ma.fsr.eda.billingservice.config;

import ma.fsr.eda.billingservice.event.dto.ConsultationCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConsultationCreatedEvent>
    consultationKafkaListenerContainerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId) {

        JsonDeserializer<ConsultationCreatedEvent> deserializer =
                new JsonDeserializer<>(ConsultationCreatedEvent.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        DefaultKafkaConsumerFactory<String, ConsultationCreatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);

        ConcurrentKafkaListenerContainerFactory<String, ConsultationCreatedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(factory);

        return container;
    }
}
