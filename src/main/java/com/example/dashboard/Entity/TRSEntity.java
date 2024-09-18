package com.example.dashboard.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TRS")
public class TRSEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    private double temps_total;
    @Getter
    private double temps_ouverture;
    @Getter
    private double temps_requis;
    @Getter
    private double temps_net;
    @Getter
    private double temps_utile;
    @Getter
    private double temps_fonctionnement;
    @Getter
    private Date date_time;
    @Getter
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;

}
