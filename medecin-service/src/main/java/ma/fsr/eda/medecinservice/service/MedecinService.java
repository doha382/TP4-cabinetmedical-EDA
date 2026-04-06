package ma.fsr.eda.medecinservice.service;


import ma.fsr.eda.medecinservice.event.producer.MedecinEventProducer;
import ma.fsr.eda.medecinservice.model.Medecin;
import ma.fsr.eda.medecinservice.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {

    @Autowired
    MedecinRepository medecinRepository;

    @Autowired
    MedecinEventProducer medecinEventProducer;

    public Medecin create(Medecin medecin) throws Exception {
        if (medecin.getNom() == null || medecin.getNom().isBlank()) {
            throw new Exception("Le nom du médecin est obligatoire.");
        }

        if(medecin.getSpecialite() == null) {
            throw new Exception("La spécialité du médecin est obligatoire.");
        }
        if(medecin.getEmail() == null) {
            throw new Exception("L’email du médecin est obligatoire.");
        }
        if(!medecin.getEmail().contains("@")) {
            throw new Exception("Email du médecin invalide.");
        }
        medecin.setDateCreation(LocalDateTime.now());
        Medecin medecinSaved = medecinRepository.save(medecin);

        medecinEventProducer.publishMedecinCreated(medecinSaved);

        return medecinSaved;
    }



    public Medecin update(Medecin medecin) throws Exception {
        Optional<Medecin> m = medecinRepository.findById(medecin.getId());
        if(!m.isPresent()){
            throw new Exception("Medecin introvable");
        }
        return medecinRepository.save(medecin);
    }

    public Medecin getMedecin(Long id) throws Exception {
        Optional<Medecin> medecin = medecinRepository.findById(id);
        if(medecin.isEmpty()){
            throw new Exception("Médecin introuvable : id = " +id);
        }
        return medecin.get();
    }

    public List<Medecin> list() {
        return medecinRepository.findAll();
    }

    public void delete(Long idMedecin){
        Medecin medecin = medecinRepository.findById(idMedecin).orElse(null);
        if (medecin != null) {
            medecinRepository.delete(medecin);
        }
    }
}

