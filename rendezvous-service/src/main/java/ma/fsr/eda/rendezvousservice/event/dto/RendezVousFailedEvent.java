package ma.fsr.eda.rendezvousservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.fsr.eda.rendezvousservice.model.RendezVous;

@AllArgsConstructor
@Data
public class RendezVousFailedEvent {
    private String eventId;
    private String error;
    private RendezVous rendezVousInfo;
}
