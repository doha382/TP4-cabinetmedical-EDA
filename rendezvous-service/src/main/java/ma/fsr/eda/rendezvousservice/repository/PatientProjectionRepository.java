package ma.fsr.eda.rendezvousservice.repository;

import ma.fsr.eda.rendezvousservice.model.projection.PatientProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientProjectionRepository extends JpaRepository<PatientProjection, Long> {
}
