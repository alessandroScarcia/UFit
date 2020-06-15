package it.sms1920.spqs.ufit.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Class that contains the Stats of the user. This Class is based on room
 */
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

    @ColumnInfo(name = "armMeasure")
    private float armMeasure;

    @ColumnInfo(name = "chestMeasure")
    private float chestMeasure;

    @ColumnInfo(name = "waistMeasure")
    private float waistMeasure;

    @ColumnInfo(name = "tightMeasure")
    private float tightMeasure;

    @ColumnInfo(name = "calveMeasure")
    private float calveMeasure;

    @ColumnInfo(name = "dateArmDetection")
    private String dateArmDetection;

    @ColumnInfo(name = "dateChestDetection")
    private String dateChestDetection;

    @ColumnInfo(name = "dateWaistDetection")
    private String dateWaistDetection;

    @ColumnInfo(name = "dateTightDetection")
    private String dateTightDetection;

    @ColumnInfo(name = "dateCalveDetection")
    private String dateCalveDetection;

    public float getArmMeasure() {
        return armMeasure;
    }

    public void setArmMeasure(float armMeasure) {
        this.armMeasure = armMeasure;
    }

    public float getChestMeasure() {
        return chestMeasure;
    }

    public void setChestMeasure(float chestMeasure) {
        this.chestMeasure = chestMeasure;
    }

    public float getWaistMeasure() {
        return waistMeasure;
    }

    public void setWaistMeasure(float waistMeasure) {
        this.waistMeasure = waistMeasure;
    }

    public float getTightMeasure() {
        return tightMeasure;
    }

    public void setTightMeasure(float tightMeasure) {
        this.tightMeasure = tightMeasure;
    }

    public float getCalveMeasure() {
        return calveMeasure;
    }

    public void setCalveMeasure(float calveMeasure) {
        this.calveMeasure = calveMeasure;
    }

    public String getDateArmDetection() {
        return dateArmDetection;
    }

    public void setDateArmDetection(String dateArmDetection) {
        this.dateArmDetection = dateArmDetection;
    }

    public String getDateChestDetection() {
        return dateChestDetection;
    }

    public void setDateChestDetection(String dateChestDetection) {
        this.dateChestDetection = dateChestDetection;
    }

    public String getDateWaistDetection() {
        return dateWaistDetection;
    }

    public void setDateWaistDetection(String dateWaistDetection) {
        this.dateWaistDetection = dateWaistDetection;
    }

    public String getDateTightDetection() {
        return dateTightDetection;
    }

    public void setDateTightDetection(String dateTightDetection) {
        this.dateTightDetection = dateTightDetection;
    }

    public String getDateCalveDetection() {
        return dateCalveDetection;
    }

    public void setDateCalveDetection(String dateCalveDetection) {
        this.dateCalveDetection = dateCalveDetection;
    }

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
