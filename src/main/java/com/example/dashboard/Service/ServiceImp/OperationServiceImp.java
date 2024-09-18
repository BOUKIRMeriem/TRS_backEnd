package com.example.dashboard.Service.ServiceImp;


import com.example.dashboard.Entity.OperationEntity;
import com.example.dashboard.Repository.OperationRepository;
import com.example.dashboard.Service.OperationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@RequiredArgsConstructor
@Service
public class OperationServiceImp implements OperationService {

    private final  OperationRepository operationRepository;
    @Override
    public OperationEntity save(OperationEntity operation) {
        return operationRepository.save(operation);
    }
    @Override
    public List<OperationEntity> getAll() {
        return operationRepository.findAll();
    }
    @Override
    public OperationEntity update(Long ids, OperationEntity operation) {
        OperationEntity existing =operationRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Operation with ID " + ids + " not found"));
        existing.setDesignation_operation(operation.getDesignation_operation());
        existing.setTemps_operation_reel(operation.getTemps_operation_reel());
        existing.setTemps_operation_theorique(operation.getTemps_operation_theorique());
        operationRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        OperationEntity  existing =operationRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Operation with ID " + ids + " not found"));
        operationRepository.delete(existing);

    }
}
