package com.example.dashboard.Repository;

import com.example.dashboard.Entity.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<ProduitEntity,Long> {
}
