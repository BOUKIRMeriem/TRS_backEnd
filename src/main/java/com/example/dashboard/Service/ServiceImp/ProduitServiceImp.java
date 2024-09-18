package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.ProduitEntity;
import com.example.dashboard.Repository.ProduitRepository;
import com.example.dashboard.Service.ProduitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ProduitServiceImp implements ProduitService {
    private final ProduitRepository produitRepository;

    @Override
    public ProduitEntity save(ProduitEntity produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<ProduitEntity> getAll() {
        return produitRepository.findAll();
    }

    @Override
    public ProduitEntity update(Long ids, ProduitEntity produit) {
        ProduitEntity existing =produitRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Produit with ID " + ids + " not found"));
        existing.setDesignation_produit(produit.getDesignation_produit());
        produitRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        ProduitEntity  existing =produitRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Produit with ID " + ids + " not found"));
        produitRepository.delete(existing);

    }


}
