package com.example.dashboard.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation")
public class OperationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    private String designation_operation;
    @Getter
    private double temps_operation_theorique;
    @Getter
    private double temps_operation_reel;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;
    @Getter
    @ManyToMany(mappedBy = "operations")
    private List<ProduitEntity> produits;
    @Getter
    @OneToMany(mappedBy = "operation")
    private List<QualiteEntity> qualites;
    @Getter
    @OneToMany(mappedBy = "operation")
    private List<EcartCadenceEntity> ecartCadences;
}
