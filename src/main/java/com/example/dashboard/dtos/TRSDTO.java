package com.example.dashboard.dtos;

import java.time.LocalDate;
import java.util.Date;

public class TRSDTO {
    public record Trs(
            double oee,
            double availability,
            double quality,
            double performance
    ){}
    public record TrsByDay(
            double oee,
            double availability,
            double quality,
            double performance,
            Date date
    ){}
    public record QualityByEntity(
           Long bad,
           Long good,
           LocalDate date,
           String entity
    ){}
   public  interface ArretByMonth{
        LocalDate getDate();
        int getCount();
    }
    public static class ArretsMachine {
        LocalDate date;
        long nonPlanifier;
        long planifier;
        public ArretsMachine() {
        }

        public ArretsMachine(LocalDate date, long nonPlanifier, long planifier) {
            this.date = date;
            this.nonPlanifier = nonPlanifier;
            this.planifier = planifier;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public long getNonPlanifier() {
            return nonPlanifier;
        }

        public void setNonPlanifier(long nonPlanifier) {
            this.nonPlanifier = nonPlanifier;
        }

        public long getPlanifier() {
            return planifier;
        }

        public void setPlanifier(long planifier) {
            this.planifier = planifier;
        }
    }
    public static class Availability {
        private String machine;
        private Double availability;

        public Availability(String machine, Double availability) {
            this.machine = machine;
            this.availability = availability;
        }

        public String getMachine() {
            return machine;
        }

        public void setMachine(String machine) {
            this.machine = machine;
        }

        public Double getAvailability() {
            return availability;
        }

        public void setAvailability(Double availability) {
            this.availability = availability;
        }
    }
    public static class Causes {
        private String nomArrete;
        private double duree;

        // Constructor
        public Causes(String nomArrete, double duree) {
            this.nomArrete = nomArrete;
            this.duree = duree;
        }

        // Getters and Setters (optional)
        public String getNomArrete() {
            return nomArrete;
        }

        public void setNomArrete(String nomArrete) {
            this.nomArrete = nomArrete;
        }

        public double getDuree() {
            return duree;
        }

        public void setDuree(double duree) {
            this.duree = duree;
        }
    }
    public static class CausesByMonth{
        private int  mois;
        private String nomArrete;
        private double dureeTotale;

        public CausesByMonth(int month, String nomArrete, double duree) {
            this.mois=month;
            this.nomArrete=nomArrete;
            this.dureeTotale=duree;

        }
        public int getMois() {
            return mois;
        }

        public String getNomArrete() {
            return nomArrete;
        }

        public double getDureeTotale() {
            return dureeTotale;
        }

        public void setMois(int mois) {
            this.mois = mois;
        }

        public void setNomArrete(String nomArrete) {
            this.nomArrete = nomArrete;
        }

        public void setDureeTotale(double dureeTotale) {
            this.dureeTotale = dureeTotale;
        }

    }
    public record Cards(double good, double repairable, double bad) {}
    public static class Quality {
        private Date date;
        private Long good;
        private Long bad;
        private Long repairable;

        public Quality(Date date, Long good, Long bad, Long repairable) {
            this.date = date;
            this.good = good;
            this.bad = bad;
            this.repairable = repairable;
        }

        // Getters et Setters

        public Date getDate() {
            return date;
        }

        public Long getGood() {
            return good;
        }

        public void setGood(Long good) {
            this.good = good;
        }

        public Long getBad() {
            return bad;
        }

        public void setBad(Long bad) {
            this.bad = bad;
        }

        public Long getRepairable() {
            return repairable;
        }

        public void setRepairable(Long repairable) {
            this.repairable = repairable;
        }
    }
    public record MachineQuality(String machine, double tempBad, double tempRepairable) {}
    public record OperationBYQuality(String operation,Long countBad) {}
    public record OperationBYQualityM(String operation,Long countBad,int month) {}
    public record TrsByEntity(double trs,LocalDate date) {}




}
