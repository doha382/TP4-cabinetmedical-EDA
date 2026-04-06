package ma.fsr.eda.billingservice.web;

import ma.fsr.eda.billingservice.model.Facture;
import ma.fsr.eda.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/factures")
public class FactureController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public List<Facture> list() {
        return billingService.list();
    }

    @GetMapping("/{id}")
    public Facture get(@PathVariable Long id) {
        return billingService.getById(id);
    }
}
