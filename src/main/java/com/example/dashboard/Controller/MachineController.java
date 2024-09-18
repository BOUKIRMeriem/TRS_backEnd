package com.example.dashboard.Controller;

import com.example.dashboard.Entity.MachineEntity;
import com.example.dashboard.Service.MachineService;
import com.example.dashboard.dto.MachineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/machine")
@RestController
public class MachineController {
   private final MachineService machineService;
    @PostMapping
    public MachineEntity save(@RequestBody MachineEntity machine){
       return  machineService.save(machine);
    }
    @GetMapping
    public List<MachineDTO> getAll(){
        return machineService.getAll();
    }
    @PutMapping("/{ids}")
    public MachineEntity update(@PathVariable Long ids ,@RequestBody MachineEntity machine){
        return machineService.update(ids,machine);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
         machineService.delete(ids);
    }
}
