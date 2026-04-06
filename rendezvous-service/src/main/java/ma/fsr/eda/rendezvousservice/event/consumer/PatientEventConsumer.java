package ma.fsr.eda.rendezvousservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.rendezvousservice.event.dto.PatientCreatedEvent;
import ma.fsr.eda.rendezvousservice.model.projection.PatientProjection;
import ma.fsr.eda.rendezvousservice.repository.PatientProjectionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientEventConsumer {
    private final PatientProjectionRepository repository;
    @KafkaListener(topics = "patient.created",
            containerFactory = "patientKafkaListenerContainerFactory",
            groupId = "rendezvous-group")
    public void consume(PatientCreatedEvent event) {
        log.info("PatientCreatedEvent : {}", event);
        PatientProjection projection = new PatientProjection();
        projection.setId(event.getPatientId());
        projection.setNom(event.getNom());
        projection.setTelephone(event.getTelephone());
        projection.setDateNaissance(event.getDateNaissance());
        projection.setGenre(event.getGenre());
        projection.setDateCreation(event.getDateCreation());
        repository.save(projection);
    }
}

