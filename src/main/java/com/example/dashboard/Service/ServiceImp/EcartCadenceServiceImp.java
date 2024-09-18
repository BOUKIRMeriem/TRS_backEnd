package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.EcartCadenceEntity;
import com.example.dashboard.Repository.EcartCadenceRepository;
import com.example.dashboard.Service.EcartCadenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class EcartCadenceServiceImp implements EcartCadenceService {

    private final  EcartCadenceRepository ecartCadenceRepository;
    @Override
    public EcartCadenceEntity save(EcartCadenceEntity machine) {
        return ecartCadenceRepository.save(machine);
    }
    @Override
    public List<EcartCadenceEntity> getAll() {
        return ecartCadenceRepository.findAll();
    }
    @Override
    public EcartCadenceEntity update(Long ids, EcartCadenceEntity ecartCadence) {
        EcartCadenceEntity existing =ecartCadenceRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Ecart de cadence with ID " + ids + " not found"));
        existing.setDate_time(ecartCadence.getDate_time());
        ecartCadenceRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        EcartCadenceEntity  existing =ecartCadenceRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Ecart de cadence with ID " + ids + " not found"));
        ecartCadenceRepository.delete(existing);

    }
}
