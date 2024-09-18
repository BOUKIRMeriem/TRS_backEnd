package com.example.dashboard.Service;

import com.example.dashboard.Entity.QualiteEntity;
import com.example.dashboard.dtos.TRSDTO;

import java.util.List;

public interface QualiteService {
    QualiteEntity save(QualiteEntity qualite);
    QualiteEntity update(Long ids,QualiteEntity qualite);
    void delete(Long ids);
    List<QualiteEntity> getAll();
    TRSDTO.Cards getCards(int year, int month, Long machineId);
     List<TRSDTO.Quality> getCardsByDate(int year, int month, Long machineId);
     List<TRSDTO.MachineQuality> getQualityByMachine(int year,int month);
     List<TRSDTO.OperationBYQuality >getOperationBYQuality(int year,int month,Long machineId);
     List<TRSDTO.OperationBYQualityM> getOperationBYQualityAndMonth(int year, Long machineId);


    }
