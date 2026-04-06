package ma.fsr.eda.medecinservice.event.producer;

import lombok.RequiredArgsConstructor;
import ma.fsr.eda.medecinservice.event.dto.MedecinCreatedEvent;
import ma.fsr.eda.medecinservice.model.Medecin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MedecinEventProducer {
    private final KafkaTemplate<String, MedecinCreatedEvent> kafkaTemplate;
    public void publishMedecinCreated(Medecin medecin) {
        MedecinCreatedEvent event = new MedecinCreatedEvent(
                UUID.randomUUID().toString(),
                medecin.getId(),
                medecin.getNom(),
                medecin.getSpecialite(),
                medecin.getEmail(),
                medecin.getDateCreation()
        );
        kafkaTemplate.send("medecin.created", event.getEventId(), event);
    }
}
