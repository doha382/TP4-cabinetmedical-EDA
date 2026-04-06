package ma.fsr.eda.billingservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FactureCreatedEvent {
    private String eventId;
    private Long factureId;
    private Long consultationId;
    private Long patientId;
    private Double montant;
    private String statut;
    private LocalDateTime dateCreation;
}
