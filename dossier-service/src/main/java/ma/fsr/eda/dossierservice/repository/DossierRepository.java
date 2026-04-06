package ma.fsr.eda.dossierservice.repository;

import ma.fsr.eda.dossierservice.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
    List<Dossier> findByPatientId(Long patientId);
    List<Dossier> findByConsultationId(Long consultationId);
}
