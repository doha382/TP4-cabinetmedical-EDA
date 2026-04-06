package ma.fsr.eda.rendezvousservice.model.projection;

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
public class MedecinProjection {
    @Id 
    private Long id;
    private String nom;
    private String specialite;
    private String email;
    private LocalDateTime dateCreation;
}
