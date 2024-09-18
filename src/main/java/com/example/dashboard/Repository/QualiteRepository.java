package com.example.dashboard.Repository;

import com.example.dashboard.Entity.QualiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface QualiteRepository extends JpaRepository<QualiteEntity, Long> {

    // Récupère les qualités non conformes pour une année, un mois et une machine donnés
    @Query("SELECT q " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE MONTH(q.date_time) = :month " +
            "AND YEAR(q.date_time) = :year " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0")
    List<QualiteEntity> getBadQuality(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Récupère les qualités non conformes pour un jour, un mois et une machine donnés
    @Query("SELECT q " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE MONTH(q.date_time) = :month " +
            "AND DAY(q.date_time) = :day " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0")
    List<QualiteEntity> getBadQualityByDay(@Param("month") int month, @Param("day") int day, @Param("machineId") Long machineId);

    // Compte le nombre de produits conformes par jour et par entité pour une année, un mois et une machine donnés
    @Query("SELECT DAY(q.date_time) AS day, COUNT(q) AS count, m.entity " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE YEAR(q.date_time) = :year " +
            "AND MONTH(q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 1 " +
            "GROUP BY DAY(q.date_time), m.entity")
    List<Object[]> countGoodQualityByDayAndEntity(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre de produits non conformes par jour et par entité pour une année, un mois et une machine donnés
    @Query("SELECT DAY(q.date_time) AS day, COUNT(q) AS count, m.entity " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE YEAR(q.date_time) = :year " +
            "AND MONTH(q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0 " +
            "GROUP BY DAY(q.date_time)")
    List<Object[]> countBadQualityByDayAndEntity(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre total de produits conformes pour une année, un mois et une machine donnés
    @Query("SELECT COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 1")
    int countGoodQuality(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre total de produits non conformes pour une année, un mois et une machine donnés
    @Query("SELECT COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0")
    int countBadQuality(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre total de produits réparables pour une année, un mois et une machine donnés
    @Query("SELECT COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 2")
    int countRepairableQuality(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre de produits conformes par jour pour une année, un mois et une machine donnés
    @Query("SELECT date(q.date_time), COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 1 " +
            "GROUP BY DAY(q.date_time)")
    List<Object[]> countGoodQualityByDate(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre de produits non conformes par jour pour une année, un mois et une machine donnés
    @Query("SELECT date(q.date_time), COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0 " +
            "GROUP BY DAY(q.date_time)")
    List<Object[]> countBadQualityByDate(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Compte le nombre de produits réparables par jour pour une année, un mois et une machine donnés
    @Query("SELECT date(q.date_time), COUNT(q) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o JOIN o.machine m " +
            "WHERE FUNCTION('YEAR', q.date_time) = :year " +
            "AND FUNCTION('MONTH', q.date_time) = :month " +
            "AND m.id = :machineId " +
            "AND q.conformite = 2 " +
            "GROUP BY DAY(q.date_time)")
    List<Object[]> countRepairableQualityByDate(@Param("year") int year, @Param("month") int month, @Param("machineId") Long machineId);

    // Récupère les qualités non conformes avec la désignation de la machine pour un mois et une année donnés
    @Query("SELECT q, m.designation_machine " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE MONTH(q.date_time) = :month " +
            "AND YEAR(q.date_time) = :year " +
            "AND q.conformite = 0")
    List<Object[]> getBadQualityy(@Param("year") int year, @Param("month") int month);

    // Récupère les qualités  considérées comme réparables avec la désignation de la machine pour un mois et une année donnés
    @Query("SELECT q, m.designation_machine " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE MONTH(q.date_time) = :month " +
            "AND YEAR(q.date_time) = :year " +
            "AND q.conformite = 2")
    List<Object[]> getRepairableQuality(@Param("year") int year, @Param("month") int month);

    // Compte le nombre de produits non conformes par opération pour un mois, une année et une machine donnés
    @Query("SELECT count(q), o.designation_operation " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE MONTH(q.date_time) = :month " +
            "AND YEAR(q.date_time) = :year " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0 " +
            "GROUP BY o.designation_operation ")
    List<Object[]> countBadQualityByOperation(@Param("year") int year, @Param("month") int month, @Param("machineId") Long id);

    // Compte le nombre de produits non conformes par opération et mois pour une année et une machine donnés
    @Query("SELECT count(q), o.designation_operation, MONTH(q.date_time) " +
            "FROM QualiteEntity q " +
            "JOIN q.operation o " +
            "JOIN o.machine m " +
            "WHERE YEAR(q.date_time) = :year " +
            "AND m.id = :machineId " +
            "AND q.conformite = 0 " +
            "GROUP BY o.designation_operation, MONTH(q.date_time)")
    List<Object[]> countBadQualityByOperationAndMonth(@Param("year") int year, @Param("machineId") Long id);
}
