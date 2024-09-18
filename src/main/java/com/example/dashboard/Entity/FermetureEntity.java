package com.example.dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fermeture")
public class FermetureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
    private Date date;
    @OneToMany(mappedBy = "fermeture")
    private List<MachineEntity> machines;

}
