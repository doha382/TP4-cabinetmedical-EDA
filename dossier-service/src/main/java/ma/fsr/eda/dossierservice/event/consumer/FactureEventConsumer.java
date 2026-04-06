package ma.fsr.eda.dossierservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.dossierservice.event.dto.FactureCreatedEvent;
import ma.fsr.eda.dossierservice.service.DossierService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FactureEventConsumer {

    private final DossierService dossierService;

    @KafkaListener(
            topics = "facture.created",
            containerFactory = "factureKafkaListenerContainerFactory",
            groupId = "dossier-group"
    )
    public void consume(FactureCreatedEvent event) {
        log.info("FactureCreatedEvent reçu dans dossier-service : {}", event);
        dossierService.enrichWithFacture(event);
    }
}
