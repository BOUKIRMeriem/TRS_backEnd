package com.example.dashboard.Controller;

import com.example.dashboard.Entity.ProduitEntity;
import com.example.dashboard.Service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/produit")
@RestController
public class ProduitController {

    private final ProduitService produitService;
    @PostMapping
    public ProduitEntity save(@RequestBody ProduitEntity produit){
        return  produitService.save(produit);
    }
    @GetMapping
    public List<ProduitEntity> getAll(){
        return produitService.getAll();
    }
    @PutMapping("/{ids}")
    public ProduitEntity update(@PathVariable Long ids ,@RequestBody ProduitEntity produit){
        return produitService.update(ids,produit);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        produitService.delete(ids);
    }
}
