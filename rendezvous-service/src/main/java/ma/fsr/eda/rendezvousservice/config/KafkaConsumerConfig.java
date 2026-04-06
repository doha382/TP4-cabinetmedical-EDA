package ma.fsr.eda.rendezvousservice.config;

import ma.fsr.eda.rendezvousservice.event.dto.MedecinCreatedEvent;
import ma.fsr.eda.rendezvousservice.event.dto.PatientCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PatientCreatedEvent>
    patientKafkaListenerContainerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId) {

        JsonDeserializer<PatientCreatedEvent> deserializer =
                new JsonDeserializer<>(PatientCreatedEvent.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        DefaultKafkaConsumerFactory<String, PatientCreatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PatientCreatedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(factory);

        return container;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MedecinCreatedEvent>
    medecinKafkaListenerContainerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId) {

        JsonDeserializer<MedecinCreatedEvent> deserializer =
                new JsonDeserializer<>(MedecinCreatedEvent.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        DefaultKafkaConsumerFactory<String, MedecinCreatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, MedecinCreatedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();
        container.setConsumerFactory(factory);

        return container;
    }
}