package com.example.dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="arrets_machines")
public class ArretsMachineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    private String type_arrete;
    @Getter
    private String nom_arrete;
    @Getter
    private double duree_arrete;
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;
}
