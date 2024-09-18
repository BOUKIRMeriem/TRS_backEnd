package com.example.dashboard.Service;

import com.example.dashboard.Entity.ArretsMachineEntity;
import com.example.dashboard.dtos.TRSDTO;
import java.util.List;
import java.util.Map;

public interface ArretsMachinesService {
    ArretsMachineEntity save(ArretsMachineEntity ArretsMachine);
    ArretsMachineEntity update(Long ids, ArretsMachineEntity ArretsMachine);
    void delete(Long ids);
    List<ArretsMachineEntity> getAll();
    Map<String, Double> countArretPlanifierAndNonPlanifier(int year, int month, Long machineId);
    List<TRSDTO.ArretsMachine> countArretPlanifierAndNonPlanifierByDay(int year, int month, Long machineId);
    List<TRSDTO.Availability> getMachineAvailability(int year, int month);
    List<TRSDTO.Causes> getCauses(int year,int month, Long machineId);
    List<TRSDTO.CausesByMonth> getCausesByMonth(int year,Long machineId);

}