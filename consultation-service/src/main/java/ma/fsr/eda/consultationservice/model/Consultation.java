package ma.fsr.eda.consultationservice.model;

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
public class Consultation {

    @Id
    @GeneratedValue
    private Long id;

    private Long rendezVousId;
    private Long patientId;
    private Long medecinId;

    private LocalDateTime dateConsultation;
    private String statut;
}
