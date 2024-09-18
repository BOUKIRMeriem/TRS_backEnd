package com.example.dashboard.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="qualite")
public class QualiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    @Setter
    private Long id;
    @Getter
    private int conformite;
    @Getter
    @Setter
    private Date date_time;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationEntity operation;
    @Setter
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private ProduitEntity produit;

    public void setConformite(int conformite) {
        if (conformite < 0 || conformite > 2) {
            throw new IllegalArgumentException("La valeur de conformite doit être 0, 1 ou 2.");
        }
        this.conformite = conformite;
    }
}
