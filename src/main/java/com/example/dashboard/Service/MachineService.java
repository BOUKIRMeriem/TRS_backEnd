package com.example.dashboard.Service;

import com.example.dashboard.Entity.MachineEntity;
import com.example.dashboard.dto.MachineDTO;

import java.util.List;

public interface MachineService {

    MachineEntity save(MachineEntity machine);
    MachineEntity update(Long ids,MachineEntity machine);
    void delete(Long ids);
    List<MachineDTO> getAll();
}
