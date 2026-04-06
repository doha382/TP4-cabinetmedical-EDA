package ma.fsr.eda.dossierservice.web;

import ma.fsr.eda.dossierservice.model.Dossier;
import ma.fsr.eda.dossierservice.service.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/dossiers")
public class DossierController {

    @Autowired
    private DossierService dossierService;

    @GetMapping
    public List<Dossier> list() {
        return dossierService.list();
    }

    @GetMapping("/{id}")
    public Dossier get(@PathVariable Long id) {
        return dossierService.getById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<Dossier> getByPatient(@PathVariable Long patientId) {
        return dossierService.getByPatientId(patientId);
    }
}
