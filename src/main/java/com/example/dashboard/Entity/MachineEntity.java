package com.example.dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="machine")
public class MachineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String designation_machine;
    @JsonFormat(pattern ="yyyy-MM-dd"  , shape = JsonFormat.Shape.STRING)
    private Date date_mise_en_service;
    private String entity;
    @ManyToOne
    @JoinColumn(name= "fermeture_id")
    private FermetureEntity fermeture;
    @OneToMany(mappedBy = "machine")
    private List<TRSEntity> TRS;
    @OneToMany(mappedBy ="machine")
    private List<ArretsMachineEntity>  arretsMachine;
    @OneToMany(mappedBy = "machine")
    private List<OperationEntity> operations;
}
