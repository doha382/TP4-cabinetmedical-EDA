package ma.fsr.eda.rendezvousservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientCreatedEvent {
    private String eventId;
    private Long patientId;
    private String nom;
    private LocalDate dateNaissance;
    private String telephone;
    private String genre;
    private LocalDateTime dateCreation;
}

