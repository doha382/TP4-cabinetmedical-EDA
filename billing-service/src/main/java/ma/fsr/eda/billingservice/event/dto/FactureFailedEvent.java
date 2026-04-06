package ma.fsr.eda.billingservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FactureFailedEvent {
    private String eventId;
    private Long consultationId;
    private String reason;
    private LocalDateTime dateFailure;
}
