package ma.fsr.eda.consultationservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.consultationservice.event.dto.RendezVousCreatedEvent;
import ma.fsr.eda.consultationservice.service.ConsultationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RendezVousEventConsumer {

    private final ConsultationService consultationService;

    @KafkaListener(
            topics = "rendezvous.created",
            containerFactory = "rendezvousKafkaListenerContainerFactory",
            groupId = "consultation-group"
    )
    public void consume(RendezVousCreatedEvent event) {
        log.info("RendezVousCreatedEvent reçu : {}", event);
        consultationService.createConsultationFromRdv(event);
    }
}
