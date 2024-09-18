package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.OperationEntity;
import com.example.dashboard.Entity.QualiteEntity;
import com.example.dashboard.Entity.TRSEntity;
import com.example.dashboard.Repository.QualiteRepository;
import com.example.dashboard.Repository.TRSRepository;
import com.example.dashboard.Service.TRsService;
import com.example.dashboard.dtos.TRSDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TRsServiceImp implements TRsService {

    private final TRSRepository trsRepository;
    private final QualiteRepository qualiteRepository;

    @Override
    public TRSEntity save(TRSEntity TRS) {
        return trsRepository.save(TRS);
    }

    @Override
    public List<TRSEntity> getAll() {
        return trsRepository.findAll();
    }

    @Override
    public TRSEntity update(Long ids, TRSEntity TRS) {
        TRSEntity existing =trsRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("TRS with ID " + ids + " not found"));
        existing.setTemps_net(TRS.getTemps_net());
        existing.setTemps_ouverture(TRS.getTemps_ouverture());
        existing.setDate_time(TRS.getDate_time());
        existing.setTemps_fonctionnement(TRS.getTemps_fonctionnement());
        existing.setTemps_requis(TRS.getTemps_requis());
        existing.setTemps_utile(TRS.getTemps_utile());
        existing.setTemps_total(TRS.getTemps_total());
        trsRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        TRSEntity  existing =trsRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("TRS with ID " + ids + " not found"));
        trsRepository.delete(existing);
    }
    // Retourne un objet contenant le TRS global,  la disponibilité globale ,  la qualité globale et de la performance global
    public TRSDTO.Trs getTRSGlobal(int year, int month, Long machineId) {
        // Récupère la liste des  TRS pour l'année, le mois et la machine spécifiés
        List<TRSEntity> trsList = trsRepository.getTRS(year, month, machineId);

        // Références atomiques pour calculer les moyennes globales
        AtomicReference<Double> globalOee = new AtomicReference<>(0.0);
        AtomicReference<Double> globalAvailability = new AtomicReference<>(0.0);
        double globalPerformance = 0;
        double globalQuality = 0;

        // Parcourt chaque entité TRS pour calculer les valeurs globales
        trsList.forEach(t -> {
            Double tempsUtile = t.getTemps_utile();
            Double tempsRequis = t.getTemps_requis();
            Double tempsFonctionnement = t.getTemps_fonctionnement();
            Double tempsOuverture = t.getTemps_ouverture();

            // Calcule l'OEE global si le temps requis est valide
            if (tempsRequis != null && tempsRequis > 0) {
                double tempOee = tempsUtile / tempsRequis;
                globalOee.updateAndGet(v -> v + tempOee);
            }
            // Calcule la disponibilité globale si le temps d'ouverture est valide
            if (tempsOuverture != null && tempsOuverture > 0) {
                double tempA = tempsFonctionnement / tempsOuverture;
                globalAvailability.updateAndGet(v -> v + tempA);
            }
        });
        // Récupère la liste des  qualité pour l'année, le mois et la machine spécifiés
        List<QualiteEntity> qualiteList = qualiteRepository.getBadQuality(year, month, machineId);

        // Parcourt chaque entité de qualité pour calculer la qualité globale
        for (QualiteEntity qualite : qualiteList) {
            OperationEntity operation = qualite.getOperation();
            Double tempsRequis = trsRepository.findTempsRequis(
                    qualite.getDate_time().getDate(),
                    qualite.getDate_time().getMonth() + 1,
                    machineId
            );
            // Calcule la qualité globale si le temps requis est valide
            if (tempsRequis != null && tempsRequis > 0) {
                double Quality = operation.getTemps_operation_reel() / tempsRequis;
                globalQuality += Quality;
            }
        }
        // Calcule les moyennes pour chaque métrique
        int trsListSize = trsList.size();
        int qualiteListSize = qualiteList.size();
        double averageOee = trsListSize > 0 ? globalOee.get() / trsListSize : 0;
        double averageAvailability = trsListSize > 0 ? globalAvailability.get() / trsListSize : 0;
        double averageQuality = qualiteListSize > 0 ? globalQuality / qualiteListSize : 0;

        // Retourne les valeurs moyennes
        return new TRSDTO.Trs(
                Math.round(averageOee * 10000.0) / 100.0,
                Math.round(averageAvailability * 10000.0) / 100.0,
                Math.round(averageQuality * 10000.0) / 100.0,
                0
        );
    }
    // Retourne une liste d'objets  contenant les valeurs TRS, la disponibilité, la qualité et la performance pour chaque jour
    public List<TRSDTO.TrsByDay> getTRSByDay(int year, int month, Long machineId) {
        // Récupère la liste des entités TRS pour l'année, le mois et la machine spécifiés
        List<TRSEntity> trsList = trsRepository.getTRS(year, month, machineId);

        // Crée une liste pour stocker les objets TRSDTO.TrsByDay
        List<TRSDTO.TrsByDay> trsDTOList = new ArrayList<>();

        // Parcourt chaque entité TRS
        for (TRSEntity t : trsList) {
            // Calcule TRS
            double tempOee = Math.round((t.getTemps_utile() / t.getTemps_requis()) * 10000.0) / 100.0;

            // Calcule la disponibilité
            double tempA = Math.round((t.getTemps_fonctionnement() / t.getTemps_ouverture()) * 10000.0) / 100.0;

            // Récupère la date et le jour de l'entité TRS
            Date dateTime = t.getDate_time();
            int day = dateTime.toInstant().atZone(ZoneId.systemDefault()).getDayOfMonth();

            // Initialise la variable pour calculer la qualité
            double globalQuality = 0;

            // Récupère la liste des entités de qualité pour le jour, le mois et la machine spécifiés
            List<QualiteEntity> qualiteList = qualiteRepository.getBadQualityByDay(month, day, machineId);

            // Parcourt chaque entité de qualité pour calculer la qualité
            for (QualiteEntity qualite : qualiteList) {
                OperationEntity operation = qualite.getOperation();
                Double tempsRequis = t.getTemps_requis();

                // Calcule la qualité si le temps requis est valide
                if (tempsRequis != null && tempsRequis > 0) {
                    globalQuality += operation.getTemps_operation_reel() / tempsRequis;
                }
            }

            // Crée un objet TRSDTO.TrsByDay avec les valeurs calculées et l'ajoute à la liste
            TRSDTO.TrsByDay dto = new TRSDTO.TrsByDay(tempOee, tempA, Math.round(globalQuality * 10000.0) / 100.0, 0, dateTime);
            trsDTOList.add(dto);
        }
        return trsDTOList;
    }
    // Retourne une liste d'objets  contenant les nombres de produits conformes et non conformes
    // par jour et par entité, pour une année, un mois et une machine donnés.
    public List<TRSDTO.QualityByEntity> getGoodAndBadQuality(int year, int month, Long machineId) {
        // Récupère le nombre de produits conformes par jour et par entité pour une année, un mois et une machine donnés
        List<Object[]> goodQuality = qualiteRepository.countGoodQualityByDayAndEntity(year, month, machineId);

        // Récupère le nombre de produits non conformes par jour et par entité pour une année, un mois et une machine donnés
        List<Object[]> badQuality = qualiteRepository.countBadQualityByDayAndEntity(year, month, machineId);

        // Crée une Map pour stocker les objets QualityByEntity par jour
        Map<Integer, TRSDTO.QualityByEntity> qualityMap = new HashMap<>();

        // Parcourt les résultats de qualité conforme et les ajoute à la Map
        for (Object[] result : goodQuality) {
            int day = (Integer) result[0]; // Jour du mois
            long count = (Long) result[1]; // Nombre de produits conformes
            String entity = (String) result[2]; // Entité associée

            // Récupère l'objet existant pour ce jour ou en crée un nouveau si inexistant
            TRSDTO.QualityByEntity existingDto = qualityMap.get(day);
            LocalDate date = LocalDate.of(year, month, day);
            if (existingDto == null) {
                existingDto = new TRSDTO.QualityByEntity(0L, 0L, date, entity);
            }
            // Met à jour la carte avec le nombre de produits conformes pour ce jour
            qualityMap.put(day, new TRSDTO.QualityByEntity(existingDto.bad(), count, existingDto.date(), entity));
        }
        // Parcourt les résultats de qualité non conforme et les ajoute à la Map
        for (Object[] result : badQuality) {
            int day = (Integer) result[0];
            long count = (Long) result[1];
            String entity = (String) result[2];

            LocalDate date = LocalDate.of(year, month, day);
            TRSDTO.QualityByEntity existingDto = qualityMap.get(day);
            if (existingDto == null) {
                existingDto = new TRSDTO.QualityByEntity(0L, 0L, date, entity);
            }
            qualityMap.put(day, new TRSDTO.QualityByEntity(count, existingDto.good(), existingDto.date(), entity));
        }
        // Crée une liste avec les valeurs de la Map et la retourne
        List<TRSDTO.QualityByEntity> trsDTOList = new ArrayList<>(qualityMap.values());
        return trsDTOList;
    }


    // Retourne une liste d'objets contenant le TRS de chaque machine pour une entité donnée
    public Map<String, List<TRSDTO.TrsByEntity>> getTrsByEntity(int year, int month, String entity) {
        // Récupère la liste des  TRS  en fonction de l'année, du mois et de l'entité spécifiés
        List<TRSEntity> listTrs = trsRepository.getTrsByEntity(year, month, entity);

        // Initialise une Map pour stocker les résultats
        Map<String, List<TRSDTO.TrsByEntity>> result = new HashMap<>();

        // Parcourt chaque  TRS récupérée
        for (TRSEntity t : listTrs) {
            // Calcule le TRS
            double trs = Math.round((t.getTemps_utile() / t.getTemps_requis()) * 10000.0) / 100.0;

            LocalDate date = t.getDate_time().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Récupère la désignation de la machine associée à  TRS
            String machine = t.getMachine().getDesignation_machine();

            // Crée un DTO pour  TRS
            TRSDTO.TrsByEntity dto = new TRSDTO.TrsByEntity(trs, date);

            // Ajoute le DTO à la liste associée à la machine dans Map
            result.computeIfAbsent(machine, k -> new ArrayList<>()).add(dto);
        }

        // Retourne Map  contenant les TRS par machine
        return result;
    }



}











