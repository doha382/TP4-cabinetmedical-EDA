package ma.fsr.eda.rendezvousservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateRdv;

    private Long patientId;   // juste l’ID
    private Long medecinId;   // juste l’ID

    @Enumerated(EnumType.STRING)
    private StatutRdv statut;

    private LocalDateTime dateCreation;
}

