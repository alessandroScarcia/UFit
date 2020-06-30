package it.sms1920.spqs.ufit.launcher.userstats;

import android.content.Context;


/**
 * Contract used to set the content inside the fragment of the Stats
 */
public interface StatsContract {

    /**
     * Interface used to show the different content of the view (in base of the tab selected)
     */
    interface View {
        void showGeneralStats();

        void showBodyStats();

        void setWeight(String valueOf);

        void setWeightDate(String valueOf);

        void setFat(String valueOf);

        void setFatDate(String valueOf);

        void setWater(String valueOf);

        void setWaterDate(String valueOf);

        void setMuscle(String valueOf);

        void setMuscleDate(String valueOf);

        void setArm(String valueOf);

        void setArmDate(String valueOf);

        void setChest(String valueOf);

        void setChestDate(String valueOf);

        void setWaist(String valueOf);

        void setWaistDate(String valueOf);

        void setTight(String valueOf);

        void setCalve(String valueOf);

        void setTightDate(String valueOf);

        void setCalveDate(String valueOf);

        void setFFMI(String valueOf);

        void setBMI(String valueOf);

        void setBMIStatus(float strBMIStatus);

        void setFFMIStatus(float strFFMIStatus);

        void setHeight(String valueOf);

        void setHeightDate(String valueOf);
    }


    /**
     * Interface used to elaborate the date from the View and the database used in the model
     */
    interface Presenter {
        void onTabSelectedAtPosition(int position);

        void setDatabase(Context context);

        float calculateBMI();

        float calculateFFMI(float weight, float bodyFat);

        void addNewIdStats(int ID);

        void getData();

//        void deleteRecordStats(int id);

        void updateWeight(float weight, String dateWeightDetection);

        void updateFat(float fat, String dateFatDetection);

        void updateWater(float water, String dateWaterDetection);

        void updateMuscle(float muscle, String dateMuscleDetection);

        void addNewUserStats();

        boolean checkExistUserStats();

        void updateArm(float value, String date);

        void updateChest(float value, String date);

        void updateWaist(float value, String date);

        void updateTight(float value, String date);

        void updateCalve(float value, String date);

        void setGeneralStats();

        void setBodyStats();

        void initializeDatabase(Context context);

        void updateHeight(float value, String date);
    }
}
