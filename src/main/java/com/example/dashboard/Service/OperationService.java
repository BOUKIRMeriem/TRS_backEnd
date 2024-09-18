package com.example.dashboard.Service;

import com.example.dashboard.Entity.OperationEntity;
import java.util.List;

public interface OperationService {
    OperationEntity save(OperationEntity operation);
    OperationEntity update(Long ids,OperationEntity operation);
    void delete(Long ids);
    List<OperationEntity> getAll();
}
