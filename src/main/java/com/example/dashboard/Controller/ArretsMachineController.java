package com.example.dashboard.Controller;

import com.example.dashboard.Entity.ArretsMachineEntity;
import com.example.dashboard.Service.ArretsMachinesService;
import com.example.dashboard.dtos.TRSDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/arretsMachine")
@RestController
public class ArretsMachineController {
    private final ArretsMachinesService arretsMachinesService;
    @PostMapping
    public ArretsMachineEntity save(@RequestBody ArretsMachineEntity arretsMachine){
        return  arretsMachinesService.save(arretsMachine);
    }
    @GetMapping
    public List<ArretsMachineEntity> getAll(){
        return arretsMachinesService.getAll();
    }
    @PutMapping("/{ids}")
    public ArretsMachineEntity update(@PathVariable Long ids ,@RequestBody ArretsMachineEntity arretsMachine){
        return arretsMachinesService.update(ids,arretsMachine);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        arretsMachinesService.delete(ids);
    }
   @GetMapping("/global/{year}/{month}/{machineId}")
    public Map<String, Double> countArretPlanifierAndNonPlanifier(@PathVariable int year, @PathVariable int month , @PathVariable Long machineId){
        return arretsMachinesService.countArretPlanifierAndNonPlanifier(year,month, machineId);
    }
    @GetMapping("/day/{year}/{month}/{machineId}")
    public List<TRSDTO.ArretsMachine> countArretPlanifierAndNonPlanifierByDay(@PathVariable int year, @PathVariable int month , @PathVariable Long machineId){
        return arretsMachinesService.countArretPlanifierAndNonPlanifierByDay(year,month, machineId);
    }
    @GetMapping("/availability/{year}/{month}")
    public List<TRSDTO.Availability> calculateAvailability(@PathVariable int year, @PathVariable int month){
        return arretsMachinesService.getMachineAvailability(year,month);
    }
    @GetMapping("/causes/{year}/{month}/{machineId}")
    public List<TRSDTO.Causes> getCauses(@PathVariable int year,@PathVariable int month,@PathVariable Long machineId){
        return arretsMachinesService.getCauses(year,month,machineId);
    }
    @GetMapping("/causesTracking/{year}/{machineId}")
    public List<TRSDTO.CausesByMonth> getCausesByMonth(@PathVariable int year,@PathVariable Long machineId){
        return arretsMachinesService.getCausesByMonth(year,machineId);
    }
}
