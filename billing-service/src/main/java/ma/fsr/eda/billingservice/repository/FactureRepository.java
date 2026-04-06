package ma.fsr.eda.billingservice.repository;

import ma.fsr.eda.billingservice.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
