package it.sms1920.spqs.ufit.launcher.userstats;

import android.content.Context;

/**
 * Contract used to set the content inside the fragment of the Stats
 */
public interface iStatsFragment {

    /**
     * Interface used to show the different content of the view (in base of the tab selected)
     */
    interface View {
        void showGeneralStats();

        void showBodyStats();
    }


    /**
     * Interface used to elaborate the date from the View and the database used in the model
     */
    interface Presenter {
        void onTabSelectedAtPosition(int position);

        void setDatabase(Context context);

        float calculateBMI(float parseFloat);

        float calculateFFMI(float weight, float bodyFat);

        void addNewIdStats(int ID);

        void getData();

        void deleteRecordStats(int id);

        void updateWeight(int idUserStats, float weight, String dateWeightDetection);

        void updateFat(int idUserStats, float fat, String dateFatDetection);

        void updateWater(int idUserStats, float water, String dateWaterDetection);

        void updateMuscle(int idUserStats, float muscle, String dateMuscleDetection);

        void addNewUserStats();

        boolean checkExistUserStats();

        void updateArm(int idUserStats, float value, String date);

        void updateChest(int idUserStats, float value, String date);

        void updateWaist(int idUserStats, float value, String date);

        void updateTight(int idUserStats, float value, String date);

        void updateCalve(int idUserStats, float value, String date);
    }
}
