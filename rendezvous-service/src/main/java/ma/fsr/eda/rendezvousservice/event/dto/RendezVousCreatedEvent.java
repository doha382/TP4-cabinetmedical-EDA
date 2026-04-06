package ma.fsr.eda.rendezvousservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.fsr.eda.rendezvousservice.model.StatutRdv;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RendezVousCreatedEvent {
    private String eventId;
    private Long rendezVousId;
    private Long patientId;
    private Long medecinId;
    private LocalDateTime dateRendezVous;
    private StatutRdv status;
    private LocalDateTime dateCreation;
}

