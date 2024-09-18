package com.example.dashboard.Repository;

import com.example.dashboard.Entity.TRSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TRSRepository extends JpaRepository<TRSEntity, Long> {

    //  récupérer les entités TRS pour une machine donnée, un mois et une année spécifiés
    @Query("SELECT trs FROM TRSEntity trs " +
            "JOIN trs.machine m " +
            "WHERE MONTH(trs.date_time) = :month " +
            "AND YEAR(trs.date_time) = :year " +
            "AND m.id = :machineId")
    List<TRSEntity> getTRS(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // récupérer le temps requis pour une journée, un mois et une machine spécifiés
    @Query("SELECT t.temps_requis FROM TRSEntity t " +
            "JOIN t.machine m " +
            "WHERE DAY(t.date_time) = :day " +
            "AND MONTH(t.date_time) = :month " +
            "AND m.id = :machineId")
    Double findTempsRequis(@Param("day") int day, @Param("month") int month, @Param("machineId") Long machineId);

    //  récupérer le total des temps d'ouverture par machine pour un mois et une année donnés
    @Query("SELECT m.designation_machine, m.id, SUM(t.temps_ouverture) " +
            "FROM TRSEntity t " +
            "JOIN t.machine m " +
            "WHERE FUNCTION('MONTH', t.date_time) = :month " +
            "AND FUNCTION('YEAR', t.date_time) = :year " +
            "GROUP BY m.id")
    List<Object[]> getTotalTempsOuverture(@Param("year") int year, @Param("month") int month);

    // récupérer les entités TRS pour un mois donnée et une année spécifiés,
    // en filtrant également par une entité spécifique
    @Query("SELECT trs FROM TRSEntity trs " +
            "JOIN trs.machine m " +
            "WHERE MONTH(trs.date_time) = :month " +
            "AND YEAR(trs.date_time) = :year " +
            "AND m.entity = :entity")
    List<TRSEntity> getTrsByEntity(@Param("year") int year, @Param("month") int month, @Param("entity") String entity);

}
