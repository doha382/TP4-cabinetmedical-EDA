package ma.fsr.eda.rendezvousservice.model.projection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class PatientProjection {
    @Id
    private Long id;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String telephone;
    private LocalDateTime dateCreation;
}
