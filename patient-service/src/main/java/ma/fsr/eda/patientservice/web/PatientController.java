package ma.fsr.eda.patientservice.web;

import ma.fsr.eda.patientservice.model.Patient;
import ma.fsr.eda.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public Patient create(@RequestBody Patient patient) throws Exception {
        return patientService.create(patient);
    }

    @GetMapping("/{id}")
    public Patient get(@PathVariable Long id) throws Exception {
        return patientService.getPatient(id);
    }

    @GetMapping
    public List<Patient> list() {
        return patientService.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        patientService.delete(id);
    }
}

