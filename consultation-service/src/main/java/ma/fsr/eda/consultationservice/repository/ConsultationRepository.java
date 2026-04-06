package ma.fsr.eda.consultationservice.repository;

import ma.fsr.eda.consultationservice.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByRendezVousId(Long rendezVousId);
}
