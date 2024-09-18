package com.example.dashboard.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produit")
public class ProduitEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    private String designation_produit;
    @Getter
    @ManyToMany
    @JoinTable(
            name = "produit_operation",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id")
    )
    private List<OperationEntity> operations;
    @OneToMany(mappedBy = "produit")
    private List<QualiteEntity> qualites;
}
