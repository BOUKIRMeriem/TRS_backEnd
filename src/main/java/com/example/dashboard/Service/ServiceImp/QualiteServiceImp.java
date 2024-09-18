package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.OperationEntity;
import com.example.dashboard.Entity.QualiteEntity;
import com.example.dashboard.Repository.QualiteRepository;
import com.example.dashboard.Service.QualiteService;
import com.example.dashboard.dtos.TRSDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class QualiteServiceImp implements QualiteService {

    private final  QualiteRepository qualiteRepository;

    @Override
    public QualiteEntity save(QualiteEntity qualite) {
        return qualiteRepository.save(qualite);
    }
    @Override
    public List<QualiteEntity> getAll() {
        return qualiteRepository.findAll();
    }



    @Override
    public QualiteEntity update(Long ids, QualiteEntity qualite) {
        QualiteEntity existing =qualiteRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException("Qualite with ID " + ids + " not found"));
        existing.setConformite(qualite.getConformite());
        existing.setDate_time(qualite.getDate_time());
        qualiteRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        QualiteEntity  existing =qualiteRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException("Qualite with ID " + ids + " not found"));
        qualiteRepository.delete(existing);
    }
    @Override
// Retourne les pourcentages des différentes qualités (conformes, réparables, non conformes) pour une machine, un mois et une année donnés
    public TRSDTO.Cards getCards(int year, int month, Long machineId) {

        // Récupère le nombre de produits de qualité conformes pour l'année, le mois et la machine spécifiés
        int goodQuality = qualiteRepository.countGoodQuality(year, month, machineId);

        // Récupère le nombre de produits de qualité non conformes
        int badQuality = qualiteRepository.countBadQuality(year, month, machineId);

        // Récupère le nombre de produits réparables
        int repairableQuality = qualiteRepository.countRepairableQuality(year, month, machineId);

        // Calcule le total des produits (qualité conformes + qualité non conformes + qualité réparables)
        long total = goodQuality + badQuality + repairableQuality;

        // Calcule le pourcentage de produits
        double goodQualityPercentage = total > 0 ? (double) goodQuality / total * 100 : 0;
        double badQualityPercentage = total > 0 ? (double) badQuality / total * 100 : 0;
        double repairableQualityPercentage = total > 0 ? (double) repairableQuality / total * 100 : 0;
        return new TRSDTO.Cards(goodQualityPercentage, repairableQualityPercentage, badQualityPercentage);
    }
    @Override
   // Retourne la liste des qualités (conformes, non conformes, réparables) pour une machine, un mois et une année donnés, groupée par date
    public List<TRSDTO.Quality> getCardsByDate(int year, int month, Long machineId) {

        // Récupère la liste des qualités conformes par date pour l'année, le mois et la machine spécifiés
        List<Object[]> listGood = qualiteRepository.countGoodQualityByDate(year, month, machineId);

        // Récupère la liste des qualités non conformes
        List<Object[]> listBad = qualiteRepository.countBadQualityByDate(year, month, machineId);

        // Récupère la liste des qualités réparables
        List<Object[]> listRepairable = qualiteRepository.countRepairableQualityByDate(year, month, machineId);

        // Crée une map pour stocker les données avec la date comme clé et un objet Quality pour les données de chaque jour
        Map<Date, TRSDTO.Quality> map = new HashMap<>();

        // Ajoute les qualités conformes à la map
        for (Object[] l : listGood) {
            Date date = (Date) l[0];
            long countGood = (long) l[1];
            // Crée une nouvelle entrée dans la map pour la date donnée avec le nombre de qualités conformes
            map.put(date, new TRSDTO.Quality(date, countGood, 0L, 0L));
        }
        // Ajoute les qualités non conformes à la map
        for (Object[] l : listBad) {
            Date date = (Date) l[0];
            long countBad = (long) l[1];
            // Si la date existe déjà dans la map, met à jour le nombre de qualités non conformes, sinon crée une nouvelle entrée
            map.computeIfAbsent(date, d -> new TRSDTO.Quality(d, 0L, 0L, 0L))
                    .setBad(countBad);
        }
        // Ajoute les qualités réparables à la map
        for (Object[] l : listRepairable) {
            Date date = (Date) l[0];
            long countRepairable = (long) l[1];
            // Si la date existe déjà dans la map, met à jour le nombre de qualités réparables, sinon crée une nouvelle entrée
            map.computeIfAbsent(date, d -> new TRSDTO.Quality(d, 0L, 0L, 0L))
                    .setRepairable(countRepairable);
        }
        return new ArrayList<>(map.values());
    }

    // Retourne les temps de qualité non conformes et réparables par machine pour une année et un mois donnés
    public List<TRSDTO.MachineQuality> getQualityByMachine(int year, int month) {
        // Récupération de la liste des qualités non conformes pour une année et un mois donnés
        List<Object[]> listBad = qualiteRepository.getBadQualityy(year, month);

        // Récupération de la liste des qualités réparables
        List<Object[]> listRepairable = qualiteRepository.getRepairableQuality(year, month);

        // Initialisation des Maps pour stocker les temps réels des opérations des machines en fonction de leur qualité
        Map<String, Double> badMap = new HashMap<>();
        Map<String, Double> repairableMap = new HashMap<>();

        // Parcours de la liste des qualités non conformes
        for (Object[] result : listBad) {
            QualiteEntity quality = (QualiteEntity) result[0]; // Extraction de l'entité qualité
            String machine = (String) result[1]; // Extraction du nom de la machine
            double tempReel = quality.getOperation().getTemps_operation_reel(); // Récupération du temps réel d'opération
            badMap.merge(machine, tempReel, Double::sum); // Ajout ou mise à jour du temps réel dans la map des qualités non conformes
        }

        // Parcours de la liste des qualités réparables
        for (Object[] l : listRepairable) {
            QualiteEntity quality = (QualiteEntity) l[0]; // Extraction de l'entité qualité
            String machine = (String) l[1]; // Extraction du nom de la machine
            double tempReel = quality.getOperation().getTemps_operation_reel(); // Récupération du temps réel d'opération
            repairableMap.merge(machine, tempReel, Double::sum); // Ajout ou mise à jour du temps réel dans la map des qualités réparables
        }

        // Création de la liste de résultats qui contiendra les informations sur les machines et leurs qualités
        List<TRSDTO.MachineQuality> resultList = new ArrayList<>();

        // Pour chaque machine dans la map des qualités non conformes
        for (String machine : badMap.keySet()) {
            double tempBad = badMap.getOrDefault(machine, 0.0); // Récupération du temps pour les qualités non conformes
            double tempRepairable = repairableMap.getOrDefault(machine, 0.0); // Récupération du temps pour les qualités réparables
            resultList.add(new TRSDTO.MachineQuality(machine, tempBad, tempRepairable)); // Ajout à la liste de résultats
        }
        return resultList;
    }
    // Retourne le nom de l'opération ainsi que le nombre de qualités non conformes
    // pour une année, un mois et une machine donnés
    public List<TRSDTO.OperationBYQuality> getOperationBYQuality(int year, int month, Long machineId) {
        // Récupération de la liste des opérations avec le nombre de qualités non conformes
        // pour une année, un mois et une machine donnés
        List<Object[]> list = qualiteRepository.countBadQualityByOperation(year, month, machineId);

        List<TRSDTO.OperationBYQuality> listDTO = new ArrayList<>();

        for (Object[] l : list) {
            // Extraction du nom de l'opération
            String operation = (String) l[1];
            // Extraction du nombre de qualités non conformes pour cette opération
            Long countBad = (Long) l[0];
            TRSDTO.OperationBYQuality dto = new TRSDTO.OperationBYQuality(operation, countBad);
            listDTO.add(dto);
        }
        return listDTO;
    }
    // Retourne le nom de l'opération ainsi que le nombre de qualités non conformes pour chaque mois
    // d'une année donnée et pour une machine spécifiée
    public List<TRSDTO.OperationBYQualityM> getOperationBYQualityAndMonth(int year, Long machineId) {
        // Récupération de la liste des opérations avec le nombre de qualités non conformes
        // pour chaque mois d'une année donnée et pour une machine spécifiée
        List<Object[]> list = qualiteRepository.countBadQualityByOperationAndMonth(year, machineId);
        List<TRSDTO.OperationBYQualityM> listDTO = new ArrayList<>();

        for (Object[] l : list) {
            // Extraction du nombre de qualités non conformes pour cette opération
            Long count = (Long) l[0];
            // Extraction du nom de l'opération
            String operation = (String) l[1];
            // Extraction du mois pour lequel les données sont fournies
            int month = (int) l[2];
            listDTO.add(new TRSDTO.OperationBYQualityM(operation, count, month));
        }
        return listDTO;
    }


}
