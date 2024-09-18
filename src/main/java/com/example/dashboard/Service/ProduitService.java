package com.example.dashboard.Service;

import com.example.dashboard.Entity.ProduitEntity;
import java.util.List;

public interface ProduitService {

    ProduitEntity save(ProduitEntity produit);

    ProduitEntity update(Long ids,ProduitEntity produit);

    void delete(Long ids);

    List<ProduitEntity> getAll();
}
