package ma.fsr.eda.consultationservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultationCreatedEvent {
    private String eventId;
    private Long consultationId;
    private Long rendezVousId;
    private Long patientId;
    private Long medecinId;
    private LocalDateTime dateConsultation;
    private String statut;
}
