package ma.fsr.eda.billingservice.model;

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
public class Facture {

    @Id
    @GeneratedValue
    private Long id;

    private Long consultationId;
    private Long patientId;
    private Double montant;
    private String statut;
    private LocalDateTime dateCreation;
}
