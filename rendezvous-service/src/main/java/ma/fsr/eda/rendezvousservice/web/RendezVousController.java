package ma.fsr.eda.rendezvousservice.web;

import ma.fsr.eda.rendezvousservice.model.StatutRdv;
import ma.fsr.eda.rendezvousservice.model.RendezVous;
import ma.fsr.eda.rendezvousservice.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @PostMapping
    public RendezVous create(@RequestBody RendezVous rdv) {
        return rendezVousService.create(rdv);
    }

    @GetMapping
    public List<RendezVous> list() {
        return rendezVousService.list();
    }

    @GetMapping("/{id}")
    public RendezVous get(@PathVariable Long id) {
        return rendezVousService.getById(id);
    }

    @PatchMapping("/{id}/statut")
    public RendezVous updateStatut(
            @PathVariable Long id,
            @RequestParam StatutRdv statut
    ) {
        return rendezVousService.updateStatut(id, statut);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rendezVousService.delete(id);
    }
}
