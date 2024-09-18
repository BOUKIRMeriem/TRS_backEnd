package com.example.dashboard.mapper;

import com.example.dashboard.Entity.MachineEntity;
import com.example.dashboard.dto.MachineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MachineMapper {
    @Mapping(source = "designation_machine", target = "designation_machine")
    @Mapping(source = "entity", target = "entity")
    MachineDTO machineEntityToMachineDTO(MachineEntity machineEntity);
}

