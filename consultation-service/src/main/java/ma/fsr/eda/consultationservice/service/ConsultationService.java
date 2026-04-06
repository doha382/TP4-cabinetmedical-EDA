package ma.fsr.eda.consultationservice.service;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.consultationservice.event.dto.RendezVousCreatedEvent;
import ma.fsr.eda.consultationservice.event.producer.ConsultationEventProducer;
import ma.fsr.eda.consultationservice.model.Consultation;
import ma.fsr.eda.consultationservice.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ConsultationService {

    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private ConsultationEventProducer eventProducer;

    public void createConsultationFromRdv(RendezVousCreatedEvent event) {

        log.info("Création consultation depuis rendezvous.created : {}", event);

        Consultation consultation = new Consultation();
        consultation.setRendezVousId(event.getRendezVousId());
        consultation.setPatientId(event.getPatientId());
        consultation.setMedecinId(event.getMedecinId());
        consultation.setDateConsultation(LocalDateTime.now());
        consultation.setStatut("CREATED");

        Consultation saved = repository.save(consultation);

        eventProducer.publishConsultationCreated(saved);
    }

    public List<Consultation> list() {
        return repository.findAll();
    }

    public Consultation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation introuvable : id = " + id));
    }

    public List<Consultation> getByRendezVousId(Long rendezVousId) {
        return repository.findByRendezVousId(rendezVousId);
    }
}
