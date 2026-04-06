package ma.fsr.eda.medecinservice.repository;

import ma.fsr.eda.medecinservice.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
}
