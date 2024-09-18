package com.example.dashboard.Controller;

import com.example.dashboard.Entity.EcartCadenceEntity;
import com.example.dashboard.Service.EcartCadenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/ecartCadence")
@RestController
public class EcartCadenceController {

   private final EcartCadenceService ecartCadenceService;
    @PostMapping
    public EcartCadenceEntity save(@RequestBody EcartCadenceEntity ecartCadence){
        return  ecartCadenceService.save(ecartCadence);
    }
    @GetMapping
    public List<EcartCadenceEntity> getAll(){
        return ecartCadenceService.getAll();
    }
    @PutMapping("/{ids}")
    public EcartCadenceEntity update(@PathVariable Long ids ,@RequestBody EcartCadenceEntity ecartCadence){
        return ecartCadenceService.update(ids,ecartCadence);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        ecartCadenceService.delete(ids);
    }
}
