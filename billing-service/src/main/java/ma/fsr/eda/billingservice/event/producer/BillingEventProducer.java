package ma.fsr.eda.billingservice.event.producer;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.billingservice.event.dto.FactureCreatedEvent;
import ma.fsr.eda.billingservice.event.dto.FactureFailedEvent;
import ma.fsr.eda.billingservice.model.Facture;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class BillingEventProducer {

    private final KafkaTemplate<String, FactureCreatedEvent> createdKafkaTemplate;
    private final KafkaTemplate<String, FactureFailedEvent> failedKafkaTemplate;

    public BillingEventProducer(
            @Qualifier("factureCreatedKafkaTemplate") KafkaTemplate<String, FactureCreatedEvent> createdKafkaTemplate,
            @Qualifier("factureFailedKafkaTemplate") KafkaTemplate<String, FactureFailedEvent> failedKafkaTemplate) {
        this.createdKafkaTemplate = createdKafkaTemplate;
        this.failedKafkaTemplate = failedKafkaTemplate;
    }

    public void publishFactureCreated(Facture facture) {
        FactureCreatedEvent event = new FactureCreatedEvent(
                UUID.randomUUID().toString(),
                facture.getId(),
                facture.getConsultationId(),
                facture.getPatientId(),
                facture.getMontant(),
                facture.getStatut(),
                facture.getDateCreation()
        );
        log.info("Publication facture.created : {}", event);
        createdKafkaTemplate.send("facture.created", event.getEventId(), event);
    }

    public void publishFactureFailed(Long consultationId, String reason) {
        FactureFailedEvent event = new FactureFailedEvent(
                UUID.randomUUID().toString(),
                consultationId,
                reason,
                LocalDateTime.now()
        );
        log.info("Publication facture.failed : {}", event);
        failedKafkaTemplate.send("facture.failed", event.getEventId(), event);
    }
}
