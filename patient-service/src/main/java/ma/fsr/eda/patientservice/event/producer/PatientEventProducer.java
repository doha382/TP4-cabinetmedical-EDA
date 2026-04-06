package ma.fsr.eda.patientservice.event.producer;

import lombok.RequiredArgsConstructor;
import ma.fsr.eda.patientservice.event.dto.PatientCreatedEvent;
import ma.fsr.eda.patientservice.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientEventProducer {
    private final KafkaTemplate<String, PatientCreatedEvent> kafkaTemplate;
    public void publishPatientCreated(Patient patient) {
        PatientCreatedEvent event = new PatientCreatedEvent(
                UUID.randomUUID().toString(),
                patient.getId(),
                patient.getNom(),
                patient.getDateNaissance(),
                patient.getTelephone(),
                patient.getGenre(),
                patient.getDateCreation()
        );
        kafkaTemplate.send("patient.created", event.getEventId(), event);
    }
}

