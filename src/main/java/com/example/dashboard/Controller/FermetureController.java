package com.example.dashboard.Controller;

import com.example.dashboard.Entity.FermetureEntity;
import com.example.dashboard.Service.FermetureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/fermeture")
@RestController
public class FermetureController {
   private final FermetureService fermetureService;
    @PostMapping
    public FermetureEntity save(@RequestBody FermetureEntity fermeture){
        return  fermetureService.save(fermeture);
    }
    @GetMapping
    public List<FermetureEntity> getAll(){
        return fermetureService.getAll();
    }
    @PutMapping("/{ids}")
    public FermetureEntity update(@PathVariable Long ids ,@RequestBody FermetureEntity fermeture){
        return fermetureService.update(ids,fermeture);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        fermetureService.delete(ids);
    }
}
