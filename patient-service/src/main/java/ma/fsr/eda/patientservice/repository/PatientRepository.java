package ma.fsr.eda.patientservice.repository;

import ma.fsr.eda.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
