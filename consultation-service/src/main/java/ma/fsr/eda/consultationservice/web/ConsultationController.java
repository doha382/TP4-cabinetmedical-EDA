package ma.fsr.eda.consultationservice.web;

import ma.fsr.eda.consultationservice.model.Consultation;
import ma.fsr.eda.consultationservice.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public List<Consultation> list() {
        return consultationService.list();
    }

    @GetMapping("/{id}")
    public Consultation get(@PathVariable Long id) {
        return consultationService.getById(id);
    }

    @GetMapping("/rendezvous/{rendezVousId}")
    public List<Consultation> getByRendezVous(@PathVariable Long rendezVousId) {
        return consultationService.getByRendezVousId(rendezVousId);
    }
}
