package ma.fsr.eda.medecinservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedecinCreatedEvent {
    private String eventId;
    private Long medecinId;
    private String nom;
    private String specialite;
    private String email;
    private LocalDateTime dateCreation;
}

