package com.example.dashboard.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ecart_cadence")
public class EcartCadenceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Long id;
    @Getter
    private Date date_time;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationEntity operation;
}
