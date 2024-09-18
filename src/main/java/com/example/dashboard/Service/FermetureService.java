package com.example.dashboard.Service;

import com.example.dashboard.Entity.FermetureEntity;
import java.util.List;

public interface FermetureService {
    FermetureEntity save(FermetureEntity fermeture);
    FermetureEntity update(Long ids,FermetureEntity fermeture);
    void delete(Long ids);
    List<FermetureEntity> getAll();
}
