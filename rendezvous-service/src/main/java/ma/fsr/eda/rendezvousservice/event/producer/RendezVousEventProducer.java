package ma.fsr.eda.rendezvousservice.event.producer;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.rendezvousservice.event.dto.RendezVousCreatedEvent;
import ma.fsr.eda.rendezvousservice.event.dto.RendezVousFailedEvent;
import ma.fsr.eda.rendezvousservice.model.RendezVous;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RendezVousEventProducer {

    private final KafkaTemplate<String, RendezVousCreatedEvent> createdKafkaTemplate;
    private final KafkaTemplate<String, RendezVousFailedEvent> failedKafkaTemplate;

    public RendezVousEventProducer(
            @Qualifier("createdKafkaTemplate") KafkaTemplate<String, RendezVousCreatedEvent> createdKafkaTemplate,
            @Qualifier("failedKafkaTemplate") KafkaTemplate<String, RendezVousFailedEvent> failedKafkaTemplate) {

        this.createdKafkaTemplate = createdKafkaTemplate;
        this.failedKafkaTemplate = failedKafkaTemplate;
    }

    public void publishRendezVousCreated(RendezVous rdv) {

        RendezVousCreatedEvent event = new RendezVousCreatedEvent(
                UUID.randomUUID().toString(),
                rdv.getId(),
                rdv.getPatientId(),
                rdv.getMedecinId(),
                rdv.getDateRdv(),
                rdv.getStatut(),
                rdv.getDateCreation()
        );

        log.info("Création du rendez-vous {}", rdv);

        createdKafkaTemplate.send("rendezvous.created", event.getEventId(), event);
    }

    public void publishRendezVousCreationFailed(String error, RendezVous rdv) {

        RendezVousFailedEvent event = new RendezVousFailedEvent(
                UUID.randomUUID().toString(),
                error,
                rdv
        );

        log.info("Erreur {} pour le rendez-vous {}", error, rdv);

        failedKafkaTemplate.send("rendezvous.failed", event.getEventId(), event);
    }
}