package ma.fsr.eda.medecinservice.web;

import ma.fsr.eda.medecinservice.service.MedecinService;
import ma.fsr.eda.medecinservice.model.Medecin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;

    @PostMapping
    public Medecin create(@RequestBody Medecin medecin) throws Exception {
        return medecinService.create(medecin);
    }

    @GetMapping("/{id}")
    public Medecin get(@PathVariable Long id) throws Exception {
        return medecinService.getMedecin(id);
    }

    @GetMapping
    public List<Medecin> list() {
        return medecinService.list();
    }

    @PutMapping("/{id}")
    public Medecin update(@PathVariable Long id, @RequestBody Medecin medecin) throws Exception {
        medecin.setId(id);
        return medecinService.update(medecin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medecinService.delete(id);
    }
}
