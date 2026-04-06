package ma.fsr.eda.medecinservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medecin {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String specialite;
    private String email;
    private LocalDateTime dateCreation;
}

