package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.FermetureEntity;
import com.example.dashboard.Repository.FermetureRepository;
import com.example.dashboard.Service.FermetureService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@RequiredArgsConstructor
@Service
public class FermetureServiceImp implements FermetureService {

    private final  FermetureRepository fermetureRepository;
    @Override
    public FermetureEntity save(FermetureEntity fermeture) {
        return fermetureRepository.save(fermeture);
    }
    @Override
    public List<FermetureEntity> getAll() {
        return fermetureRepository.findAll();
    }
    @Override
    public FermetureEntity update(Long ids, FermetureEntity fermeture) {
        FermetureEntity existing =fermetureRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Fermeture with ID " + ids + " not found"));
        existing.setDate(fermeture.getDate());
        fermetureRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        FermetureEntity  existing =fermetureRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Fermeture with ID " + ids + " not found"));
        fermetureRepository.delete(existing);

    }
}
