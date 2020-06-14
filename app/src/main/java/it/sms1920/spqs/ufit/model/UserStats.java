package it.sms1920.spqs.ufit.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "userStats")
public class UserStats {

    @PrimaryKey
    private int idUserStats;

    @ColumnInfo(name = "userWeight")
    private float weight;

    @ColumnInfo(name = "userFat")
    private float fat;

    @ColumnInfo(name = "userWater")
    private float water;

    @ColumnInfo(name = "userMuscle")
    private float muscle;

    @ColumnInfo(name = "dateWeightDetection")
    private String dateWeightDetection;

    @ColumnInfo(name = "dateMuscleDetection")
    private String dateMuscleDetection;

    @ColumnInfo(name = "dateFatDetection")
    private String dateFatDetection;

    @ColumnInfo(name = "dateWaterDetection")
    private String dateWaterDetection;

    public int getIdUserStats() {
        return idUserStats;
    }

    public float getWeight() {
        return weight;
    }

    public float getFat() {
        return fat;
    }

    public float getWater() {
        return water;
    }

    public float getMuscle() {
        return muscle;
    }

    public String getDateWeightDetection() {
        return dateWeightDetection;
    }

    public String getDateFatDetection() {
        return dateFatDetection;
    }

    public String getDateMuscleDetection() {
        return dateMuscleDetection;
    }

    public String getDateWaterDetection() {
        return dateWaterDetection;
    }


    public void setIdUserStats(int idUserStats) {
        this.idUserStats = idUserStats;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public void setMuscle(float muscle) {
        this.muscle = muscle;
    }


    public void setDateWeightDetection(String dateWeightDetection) {
        this.dateWeightDetection = dateWeightDetection;
    }

    public void setDateFatDetection(String dateFatDetection) {
        this.dateFatDetection = dateFatDetection;
    }

    public void setDateWaterDetection(String dateWaterDetection) {
        this.dateWaterDetection = dateWaterDetection;
    }

    public void setDateMuscleDetection(String dateMuscleDetection) {
        this.dateMuscleDetection = dateMuscleDetection;
    }

}
