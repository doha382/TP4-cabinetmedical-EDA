package ma.fsr.eda.dossierservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.dossierservice.event.dto.ConsultationCreatedEvent;
import ma.fsr.eda.dossierservice.service.DossierService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultationEventConsumer {

    private final DossierService dossierService;

    @KafkaListener(
            topics = "consultation.created",
            containerFactory = "consultationKafkaListenerContainerFactory",
            groupId = "dossier-group"
    )
    public void consume(ConsultationCreatedEvent event) {
        log.info("ConsultationCreatedEvent reçu dans dossier-service : {}", event);
        dossierService.createFromConsultation(event);
    }
}
