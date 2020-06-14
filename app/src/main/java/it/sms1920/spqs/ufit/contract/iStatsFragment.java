package it.sms1920.spqs.ufit.contract;

import android.content.Context;

public interface iStatsFragment {
    interface View {
        void showGeneralStats();

        void showBodyStats();


    }

    interface Presenter {
        void onTabSelectedAtPostition(int position);

        void setDatabase(Context context);

        float calculateBMI(float parseFloat);

        float calculateFFMI(float weight, float bodyFat);

        boolean addNewIdStats(int ID);

        void getData();

        void deleteRecordStats(int id);

        void updateWeight(int idUserStats, float weight, String dateWeightDetection);

        void updateFat(int idUserStats, float fat, String dateFatDetection);

        void updateWater(int idUserStats, float water, String dateWaterDetection);

        void updateMuscle(int idUserStats, float muscle, String dateMuscleDetection);

        void addNewUserStats();

        boolean checkExistUserStats();
    }
}
