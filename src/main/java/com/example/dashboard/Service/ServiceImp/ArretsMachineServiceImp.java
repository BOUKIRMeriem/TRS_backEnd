package com.example.dashboard.Service.ServiceImp;

import com.example.dashboard.Entity.ArretsMachineEntity;
import com.example.dashboard.Repository.ArretsMachinesRepository;
import com.example.dashboard.Repository.TRSRepository;
import com.example.dashboard.Service.ArretsMachinesService;
import com.example.dashboard.dtos.TRSDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ArretsMachineServiceImp implements ArretsMachinesService {

   private final ArretsMachinesRepository arretsMachinesRepository;
   private final TRSRepository trsRepository;
    @Override
    public ArretsMachineEntity save(ArretsMachineEntity ArretsMachine) {
        return arretsMachinesRepository.save(ArretsMachine);
    }
    @Override
    public ArretsMachineEntity update(Long ids, ArretsMachineEntity ArretsMachine) {
        ArretsMachineEntity existing =arretsMachinesRepository.findById(ids).orElseThrow(()-> new IllegalArgumentException(" Arrets Machine with ID " + ids + " not found"));
        existing.setType_arrete(ArretsMachine.getType_arrete());
        existing.setNom_arrete(ArretsMachine.getNom_arrete());
        existing.setDuree_arrete(ArretsMachine.getDuree_arrete());
        existing.setDate(ArretsMachine.getDate());
        arretsMachinesRepository.save(existing);
        return  existing;
    }
    @Override
    public void delete(Long ids) {
        ArretsMachineEntity existing = arretsMachinesRepository.findById(ids).orElseThrow(()-> new EntityNotFoundException(" Arrets Machine with ID " + ids + " not found"));
        arretsMachinesRepository.delete(existing);
    }
    @Override
    public List<ArretsMachineEntity> getAll() {
        return arretsMachinesRepository.findAll();
    }

    // Retourne les pourcentages des arrêts planifiés et non planifiés par rapport à l'année, au mois et à la machine spécifiés
    @Override
    public Map<String, Double> countArretPlanifierAndNonPlanifier(int year, int month, Long machineId) {
        // Récupère le nombre d'arrêts planifiés pour l'année, le mois et la machine spécifiés
        int arretPlanifier = arretsMachinesRepository.countArretPlanifier(year, month, machineId);

        // Récupère le nombre d'arrêts non planifiés pour l'année, le mois et la machine spécifiés
        int arretNonPlanifier = arretsMachinesRepository.countArretNonPlanifier(year, month, machineId);

        // Calcule le total des arrêts (planifiés + non planifiés)
        long total = arretPlanifier + arretNonPlanifier;

        // Calcule le pourcentage d'arrêts planifiés par rapport au total
        double planifierPercentage = total > 0 ? (double) arretPlanifier / total * 100 : 0;

        // Calcule le pourcentage d'arrêts non planifiés par rapport au total
        double nonPlanifierPercentage = total > 0 ? (double) arretNonPlanifier / total * 100 : 0;

        // Crée une map pour stocker les résultats avec les pourcentages calculés
        Map<String, Double> resultMap = new HashMap<>();
        resultMap.put("planifier", planifierPercentage);
        resultMap.put("nonPlanifier", nonPlanifierPercentage);

        return resultMap;
    }
    // Retourne la liste des arrêts planifiés et non planifiés pour une machine donnée par jour,
    // en fonction de l'année et du mois spécifiés.
    @Override
    public List<TRSDTO.ArretsMachine> countArretPlanifierAndNonPlanifierByDay(int year, int month, Long machineId) {
        // Récupère la liste des arrêts planifiés par date pour l'année, le mois et la machine spécifiés
        List<TRSDTO.ArretByMonth> arretPlanifier = arretsMachinesRepository.countArretPlanifierByDate(year, month, machineId);

        // Récupère la liste des arrêts non planifiés par date pour l'année, le mois et la machine spécifiés
        List<TRSDTO.ArretByMonth> arretNonPlanifier = arretsMachinesRepository.countArretNonPlanifierByDate(year, month, machineId);

        List<TRSDTO.ArretsMachine> list = new ArrayList<>();

        // Parcourt la liste des arrêts planifiés et ajoute chaque entrée à la liste des résultats
        for (TRSDTO.ArretByMonth result : arretPlanifier) {
            LocalDate date = result.getDate();
            long count = result.getCount();
            // Crée un objet ArretsMachine avec le nombre d'arrêts planifiés et 0 pour les non planifiés
            TRSDTO.ArretsMachine d = new TRSDTO.ArretsMachine(date, 0, count);
            list.add(d);
        }
        // Parcourt la liste des arrêts non planifiés
        for (TRSDTO.ArretByMonth result : arretNonPlanifier) {
            LocalDate date = result.getDate();
            long count = result.getCount();
            boolean updated = false;

            // Vérifie si la liste des résultats est vide ou si la date existe déjà dans la liste
            if (list.isEmpty()) {
                // Si la liste est vide, ajoute directement l'entrée avec le nombre d'arrêts non planifiés
                TRSDTO.ArretsMachine d = new TRSDTO.ArretsMachine(date, count, 0);
                list.add(d);
            } else {
                // Si la liste n'est pas vide, recherche l'entrée avec la même date
                for (TRSDTO.ArretsMachine dto : list) {
                    if (date.equals(dto.getDate())) {
                        // Met à jour le nombre d'arrêts non planifiés pour la date correspondante
                        dto.setNonPlanifier(count);
                        updated = true;
                        break;
                    }
                }
            }
            // Si aucune entrée avec la même date n'a été trouvée, ajoute une nouvelle entrée avec le nombre d'arrêts non planifiés
            if (!updated) {
                TRSDTO.ArretsMachine d = new TRSDTO.ArretsMachine(date, count, 0);
                list.add(d);
            }
        }
        // Retourne la liste des arrêts avec les données planifiées et non planifiées par jour
        return list;
    }

    // Retourne une liste d'objets Availability représentant la disponibilité des machines
    // pour l'année et le mois spécifiés
    public List<TRSDTO.Availability> getMachineAvailability(int year, int month) {

        // Récupère la liste des durées d'arrêt cumulées par machine pour l'année et le mois spécifiés
        List<Object[]> listSumDureeArrete = arretsMachinesRepository.getSumDureeArrete(year, month);

        // Récupère la liste du temps total d'ouverture par machine pour l'année et le mois spécifiés
        List<Object[]> listTotalTempsOuverture = trsRepository.getTotalTempsOuverture(year, month);

        // Crée une map pour stocker la durée des arrêts par machine
        Map<String, Double> mapDureeArrete = new HashMap<>();
        for (Object[] row : listSumDureeArrete) {
            String machine = (String) row[0];  // Identifiant de la machine
            Double dureeArrete = (Double) row[2];  // Durée des arrêts pour cette machine
            mapDureeArrete.put(machine, dureeArrete);  // Ajoute à la map
        }

        List<TRSDTO.Availability> availabilityList = new ArrayList<>();

        // Parcourt la liste des temps d'ouverture par machine
        for (Object[] row : listTotalTempsOuverture) {
            String machine = (String) row[0];  // Identifiant de la machine
            Double tempsOuverture = (Double) row[2];  // Temps total d'ouverture pour cette machine
            Double dureeArrete = mapDureeArrete.getOrDefault(machine, 0.0);  // Durée d'arrêt pour cette machine (0 si non présente)

            // Si le temps d'ouverture est non nul et supérieur à 0
            if (tempsOuverture != null && tempsOuverture > 0) {
                // Calcule la disponibilité
                double availability = tempsOuverture - dureeArrete;
                // Ajoute un objet Availability avec le nom de la machine et sa disponibilité calculée
                availabilityList.add(new TRSDTO.Availability(machine, availability));
            }
        }
        return availabilityList;
    }

    // Retourne une liste des causes d'arrêts avec leur nom et leur durée pour une machine donnée,
    // en fonction de l'année et du mois spécifiés
    public List<TRSDTO.Causes> getCauses(int year, int month, Long machineId) {

        // Récupère la liste des noms d'arrêts et des durées d'arrêt pour l'année, le mois et la machine spécifiés
        List<Object[]> list = arretsMachinesRepository.getNomArreteAndDuree(year, month, machineId);

        List<TRSDTO.Causes> listCauses = new ArrayList<>();

        for (Object[] l : list) {
            String nomArrete = (String) l[0];  // Nom de l'arrêt
            double duree = (double) l[1];  // Durée de l'arrêt

            // Ajoute un nouvel objet Causes avec le nom et la durée à la liste des causes
            listCauses.add(new TRSDTO.Causes(nomArrete, duree));
        }
        return listCauses;
    }

    // Retourne une liste des causes d'arrêts avec leur nom et leur durée pour une machine donnée,
    // par mois pour l'année spécifiée
    public List<TRSDTO.CausesByMonth> getCausesByMonth(int year, Long machineId) {

        // Récupère la liste des mois, des noms d'arrêts et des durées d'arrêt pour l'année et la machine spécifiées
        List<Object[]> list = arretsMachinesRepository.getNomArreteAndDureeByMonth(year, machineId);

        List<TRSDTO.CausesByMonth> listCausesTracking = new ArrayList<>();

        for (Object[] l : list) {
            int month = (int) l[0];  // Mois de l'arrêt
            String nomArrete = (String) l[1];  // Nom de l'arrêt
            double duree = (double) l[2];  // Durée de l'arrêt

            // Ajoute un nouvel objet CausesByMonth avec le mois, le nom et la durée à la liste
            listCausesTracking.add(new TRSDTO.CausesByMonth(month, nomArrete, duree));
        }
        return listCausesTracking;
    }



}
