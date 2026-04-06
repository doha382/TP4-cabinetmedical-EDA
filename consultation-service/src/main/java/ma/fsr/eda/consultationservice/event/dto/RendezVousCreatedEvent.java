package ma.fsr.eda.consultationservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RendezVousCreatedEvent {
    private String eventId;
    private Long rendezVousId;
    private Long patientId;
    private Long medecinId;
    private LocalDateTime dateRendezVous;
    private String status;
    private LocalDateTime dateCreation;
}
