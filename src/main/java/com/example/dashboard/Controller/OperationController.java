package com.example.dashboard.Controller;

import com.example.dashboard.Entity.OperationEntity;
import com.example.dashboard.Service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/operation")
@RestController
public class OperationController {
   private final OperationService operationService;
    @PostMapping
    public OperationEntity save(@RequestBody OperationEntity operation){
        return  operationService.save(operation);
    }
    @GetMapping
    public List<OperationEntity> getAll(){
        return operationService.getAll();
    }
    @PutMapping("/{ids}")
    public OperationEntity update(@PathVariable Long ids ,@RequestBody OperationEntity operation){
        return operationService.update(ids,operation);
    }
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable Long ids){
        operationService.delete(ids);
    }
}
