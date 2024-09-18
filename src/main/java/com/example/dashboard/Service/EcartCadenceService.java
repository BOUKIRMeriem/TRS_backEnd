package com.example.dashboard.Service;

import com.example.dashboard.Entity.EcartCadenceEntity;
import java.util.List;

public interface EcartCadenceService {

    EcartCadenceEntity save(EcartCadenceEntity ecartCadence);
    EcartCadenceEntity update(Long ids,EcartCadenceEntity ecartCadence);
    void delete(Long ids);
    List<EcartCadenceEntity> getAll();
}
