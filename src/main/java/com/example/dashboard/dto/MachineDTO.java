package com.example.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineDTO {
    private Long id;
    private String designation_machine;
    private String entity;
}
