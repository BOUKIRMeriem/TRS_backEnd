package com.example.dashboard.Service;

import com.example.dashboard.Entity.TRSEntity;
import com.example.dashboard.dtos.TRSDTO;
import java.util.List;
import java.util.Map;

public interface TRsService {
    TRSEntity save(TRSEntity TRS);
    TRSEntity update(Long ids,TRSEntity TRS);
    void delete(Long ids);
    List<TRSEntity> getAll();
     TRSDTO.Trs getTRSGlobal(int year, int month,Long machineId);
     List<TRSDTO.TrsByDay> getTRSByDay(int year, int month, Long machineId);
     List<TRSDTO.QualityByEntity> getGoodAndBadQuality(int year, int month, Long machineId);
    Map<String, List<TRSDTO.TrsByEntity>> getTrsByEntity(int year, int month, String entity);



    }
