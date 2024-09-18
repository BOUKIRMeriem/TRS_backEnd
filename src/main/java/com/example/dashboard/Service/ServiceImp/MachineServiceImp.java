package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.MachineEntity;
import com.example.dashboard.Repository.MachineRepository;
import com.example.dashboard.Service.MachineService;
import com.example.dashboard.dto.MachineDTO;
import com.example.dashboard.mapper.MachineMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MachineServiceImp implements MachineService {
    private final MachineRepository  machineRepository;
    private final MachineMapper machineMapper;

    @Override
    public MachineEntity save(MachineEntity machine) {
        return machineRepository.save(machine);
    }

    @Override
    public List<MachineDTO> getAll() {
        List<MachineEntity> machineEntityList = machineRepository.findAll();
        return machineEntityList.stream().map(machineMapper::machineEntityToMachineDTO).collect(Collectors.toList());
    }
    @Override
    public MachineEntity update(Long ids, MachineEntity machine) {
     MachineEntity existing =machineRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Machine with ID " + ids + " not found"));
        existing.setDate_mise_en_service(machine.getDate_mise_en_service());
        existing.setDesignation_machine(machine.getDesignation_machine());
      machineRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
      MachineEntity  existing =machineRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Machine with ID " + ids + " not found"));
        machineRepository.delete(existing);
    }




}
