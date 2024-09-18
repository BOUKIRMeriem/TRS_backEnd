package com.example.dashboard.Controller;

import com.example.dashboard.Entity.TRSEntity;
import com.example.dashboard.Service.TRsService;
import com.example.dashboard.dtos.TRSDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/TRS")
@RestController
public class TRSController {

    private final TRsService tRsService;
    @PostMapping
    public TRSEntity save(@RequestBody TRSEntity TRS){
        return  tRsService.save(TRS);
    }

    @GetMapping
    public List<TRSEntity> getAll(){
        return tRsService.getAll();
    }

    @PutMapping("/{ids}")
    public TRSEntity update(@PathVariable Long ids ,@RequestBody TRSEntity TRS){
        return tRsService.update(ids,TRS);
    }

    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        tRsService.delete(ids);
    }
    @GetMapping("/global/{year}/{month}/{machineId}")
    public TRSDTO.Trs getTRSGlobal(@PathVariable int year, @PathVariable int month,@PathVariable Long machineId){
        return tRsService.getTRSGlobal(year,month,machineId);
    }
    @GetMapping("/day/{year}/{month}/{machineId}")
    public List<TRSDTO.TrsByDay> getTRSByDay(@PathVariable int year, @PathVariable int month,@PathVariable Long machineId){
        return tRsService.getTRSByDay(year ,month,machineId);
    }
    @GetMapping("/{year}/{month}/{machineId}")
    public List<TRSDTO.QualityByEntity>  getGoodAndBadQuality(@PathVariable int year,@PathVariable int month,@PathVariable Long machineId){
        return tRsService.getGoodAndBadQuality(year,month,machineId);
    }
    @GetMapping("/entity/{year}/{month}/{entity}")
    public Map<String, List<TRSDTO.TrsByEntity>> getTrsByEntity(@PathVariable int year, @PathVariable int month, @PathVariable String entity){
        return tRsService.getTrsByEntity(year,month,entity);
    }


}
