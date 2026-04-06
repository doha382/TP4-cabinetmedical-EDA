package ma.fsr.eda.billingservice.service;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.billingservice.event.dto.ConsultationCreatedEvent;
import ma.fsr.eda.billingservice.event.producer.BillingEventProducer;
import ma.fsr.eda.billingservice.model.Facture;
import ma.fsr.eda.billingservice.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BillingService {

    @Autowired
    private FactureRepository repository;

    @Autowired
    private BillingEventProducer eventProducer;

    public void generateFacture(ConsultationCreatedEvent event) {
        try {
            log.info("Génération facture pour consultation : {}", event.getConsultationId());

            Facture facture = new Facture();
            facture.setConsultationId(event.getConsultationId());
            facture.setPatientId(event.getPatientId());
            facture.setMontant(200.0);
            facture.setStatut("CREATED");
            facture.setDateCreation(LocalDateTime.now());

            Facture saved = repository.save(facture);

            eventProducer.publishFactureCreated(saved);

        } catch (Exception e) {
            log.error("Erreur génération facture : {}", e.getMessage());
            eventProducer.publishFactureFailed(event.getConsultationId(), "Erreur génération facture : " + e.getMessage());
        }
    }

    public List<Facture> list() {
        return repository.findAll();
    }

    public Facture getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable : id = " + id));
    }
}
