package com.example.dashboard.Repository;

import com.example.dashboard.Entity.ArretsMachineEntity;
import com.example.dashboard.dtos.TRSDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArretsMachinesRepository extends JpaRepository<ArretsMachineEntity ,Long> {

    // Compte le nombre d'arrêts planifiés pour une machine spécifique dans un mois et une année donnés
    @Query("SELECT COUNT(a) FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE m.id = :machineId " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "AND a.type_arrete = 'planifier' ")
    int countArretPlanifier(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre d'arrêts non planifiés pour une machine spécifique dans un mois et une année donnés
    @Query("SELECT COUNT(a) FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE m.id = :machineId " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "AND a.type_arrete = 'non planifier' ")
    int countArretNonPlanifier(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre d'arrêts planifiés par date pour une machine spécifique dans un mois et une année donnés
    @Query("SELECT date(a.date) as date, COUNT(a) as count FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE m.id = :machineId " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "AND a.type_arrete = 'planifier' GROUP BY a.date" )
    List<TRSDTO.ArretByMonth> countArretPlanifierByDate(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre d'arrêts non planifiés par date pour une machine spécifique dans un mois et une année donnés
    @Query("SELECT date(a.date) as date, COUNT(a) as count FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE m.id = :machineId " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "AND a.type_arrete = 'non planifier' " +
            "GROUP BY date(a.date)")
    List<TRSDTO.ArretByMonth> countArretNonPlanifierByDate(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Calcule la durée totale des arrêts pour chaque machine dans un mois et une année donnés
    @Query("SELECT m.designation_machine, m.id, SUM(a.duree_arrete) " +
            "FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "GROUP BY m.id")
    List<Object[]> getSumDureeArrete(@Param("year") int year, @Param("month") int month);

    // Récupère le nom des arrêts et la durée totale pour chaque type d'arrêt pour une machine spécifique dans un mois et une année donnés
    @Query("SELECT a.nom_arrete, SUM(a.duree_arrete) " +
            "FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE YEAR(a.date) = :year " +
            "AND MONTH(a.date) = :month " +
            "AND m.id = :machineId " +
            "GROUP BY a.nom_arrete " +
            "ORDER BY SUM(a.duree_arrete) DESC")
    List<Object[]> getNomArreteAndDuree(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Récupère le nom des arrêts et la durée totale par mois pour une machine spécifique dans une année donnée
    @Query("SELECT FUNCTION('MONTH', a.date), a.nom_arrete, SUM(a.duree_arrete) " +
            "FROM ArretsMachineEntity a " +
            "JOIN a.machine m " +
            "WHERE YEAR(a.date) = :year " +
            "AND m.id = :machineId " +
            "GROUP BY FUNCTION('MONTH', a.date), a.nom_arrete " +
            "ORDER BY FUNCTION('MONTH', a.date), a.nom_arrete")
    List<Object[]> getNomArreteAndDureeByMonth(@Param("year") int year, @Param("machineId") Long machineId);
}
