package ma.fsr.eda.consultationservice.event.producer;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.consultationservice.event.dto.ConsultationCreatedEvent;
import ma.fsr.eda.consultationservice.model.Consultation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ConsultationEventProducer {

    private final KafkaTemplate<String, ConsultationCreatedEvent> kafkaTemplate;

    public ConsultationEventProducer(KafkaTemplate<String, ConsultationCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishConsultationCreated(Consultation consultation) {

        ConsultationCreatedEvent event = new ConsultationCreatedEvent(
                UUID.randomUUID().toString(),
                consultation.getId(),
                consultation.getRendezVousId(),
                consultation.getPatientId(),
                consultation.getMedecinId(),
                consultation.getDateConsultation(),
                consultation.getStatut()
        );

        log.info("Publication consultation.created : {}", event);
        kafkaTemplate.send("consultation.created", event.getEventId(), event);
    }
}
