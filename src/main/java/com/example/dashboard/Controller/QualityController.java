package com.example.dashboard.Controller;

import com.example.dashboard.Entity.QualiteEntity;
import com.example.dashboard.Service.QualiteService;
import com.example.dashboard.dtos.TRSDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/quality")
@RestController
public class QualityController {
    private final QualiteService qualiteService;
    @PostMapping
    public QualiteEntity save(@RequestBody QualiteEntity quality){
        return  qualiteService.save(quality);
    }
    @GetMapping
    public List<QualiteEntity> getAll(){
        return qualiteService.getAll();
    }
    @PutMapping("/{ids}")
    public QualiteEntity update(@PathVariable Long ids ,@RequestBody QualiteEntity quality){
        return qualiteService.update(ids,quality);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        qualiteService.delete(ids);
    }
    @GetMapping("/{year}/{month}/{machineId}")
    public TRSDTO.Cards  getCards(@PathVariable int year,@PathVariable int month ,@PathVariable Long machineId){
        return qualiteService.getCards(year,month,machineId);
    }
    @GetMapping("/date/{year}/{month}/{machineId}")
    public List<TRSDTO.Quality> getCardsByDate(@PathVariable int year,@PathVariable int month ,@PathVariable Long machineId){
        return qualiteService.getCardsByDate(year,month,machineId);
    }
    @GetMapping("/{year}/{month}")
    public List<TRSDTO.MachineQuality> getQualityByMachine(@PathVariable int year,@PathVariable int month ){
        return qualiteService.getQualityByMachine(year,month);
    }
    @GetMapping("/operation/{year}/{month}/{machineId}")
    public List<TRSDTO.OperationBYQuality> getOperationBYQuality(@PathVariable int year,@PathVariable int month,@PathVariable Long machineId ){
        return qualiteService.getOperationBYQuality(year,month,machineId);
    }
    @GetMapping("/operation/month/{year}/{machineId}")
    public List<TRSDTO.OperationBYQualityM> getOperationBYQualityAndMonth(@PathVariable int year,@PathVariable Long machineId ){
        return qualiteService.getOperationBYQualityAndMonth(year,machineId);
    }

}
