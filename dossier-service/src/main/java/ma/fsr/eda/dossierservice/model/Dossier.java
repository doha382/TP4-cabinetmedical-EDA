package ma.fsr.eda.dossierservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Dossier {

    @Id
    @GeneratedValue
    private Long id;

    private Long patientId;
    private Long consultationId;
    private Long rendezVousId;
    private Long medecinId;

    private LocalDateTime dateConsultation;
    private String statutConsultation;

    private Long factureId;
    private Double montantFacture;
    private String statutFacture;

    private LocalDateTime dateCreation;
}
