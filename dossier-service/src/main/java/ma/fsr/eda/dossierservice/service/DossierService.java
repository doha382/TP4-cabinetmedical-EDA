package ma.fsr.eda.dossierservice.service;

import lombok.extern.slf4j.Slf4j;
import ma.fsr.eda.dossierservice.event.dto.ConsultationCreatedEvent;
import ma.fsr.eda.dossierservice.event.dto.FactureCreatedEvent;
import ma.fsr.eda.dossierservice.model.Dossier;
import ma.fsr.eda.dossierservice.repository.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DossierService {

    @Autowired
    private DossierRepository repository;

    public void createFromConsultation(ConsultationCreatedEvent event) {
        log.info("Création dossier depuis consultation.created : {}", event);

        Dossier dossier = new Dossier();
        dossier.setPatientId(event.getPatientId());
        dossier.setConsultationId(event.getConsultationId());
        dossier.setRendezVousId(event.getRendezVousId());
        dossier.setMedecinId(event.getMedecinId());
        dossier.setDateConsultation(event.getDateConsultation());
        dossier.setStatutConsultation(event.getStatut());
        dossier.setDateCreation(LocalDateTime.now());

        repository.save(dossier);
    }

    public void enrichWithFacture(FactureCreatedEvent event) {
        log.info("Enrichissement dossier depuis facture.created : {}", event);

        List<Dossier> dossiers = repository.findByConsultationId(event.getConsultationId());

        if (dossiers.isEmpty()) {
            log.warn("Aucun dossier trouvé pour consultationId={}", event.getConsultationId());
            return;
        }

        Dossier dossier = dossiers.get(0);
        dossier.setFactureId(event.getFactureId());
        dossier.setMontantFacture(event.getMontant());
        dossier.setStatutFacture(event.getStatut());

        repository.save(dossier);
    }

    public List<Dossier> list() {
        return repository.findAll();
    }

    public Dossier getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dossier introuvable : id = " + id));
    }

    public List<Dossier> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }
}
