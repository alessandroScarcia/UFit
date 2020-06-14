//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import android.content.Context;


import java.util.List;
import java.util.Random;

import it.sms1920.spqs.ufit.contract.iStatsFragment;
import it.sms1920.spqs.ufit.model.Stats;
import it.sms1920.spqs.ufit.model.User;
import it.sms1920.spqs.ufit.model.UserStats;

import static java.sql.Types.NULL;


public class StatsPresenter implements iStatsFragment.Presenter {
    private static final float NULL_STATS = 0;
    private Stats statsModel;
    private final iStatsFragment.View view;
    public static UserStats userStats;

    public StatsPresenter(iStatsFragment.View view) {
        this.view = view;
    }


    /**
     * Function used to show the correct content in frame layout
     *
     * @param position
     */
    @Override
    public void onTabSelectedAtPostition(int position) {
        if (position == 0) {
            view.showGeneralStats();
        } else if (position == 1) {
            view.showBodyStats();
        } else {
            throw new IllegalArgumentException("Invalid value for argument position.");
        }
    }

    /**
     * Setting database inside the model
     *
     * @param context
     */
    @Override
    public void setDatabase(Context context) {
        statsModel = new Stats(this);
        statsModel.createDatabase(context);
        userStats = new UserStats();
    }

    /**
     * Function to calculate the BMI
     *
     * @param weight
     * @return
     */
    @Override
    public float calculateBMI(float weight) {
        float BMI = 0 ;
//        User user = new User();
//
//
//        if (user.getHeight() != 0) {
//            float heightM = (float) user.getHeight() / 10;
//            BMI = weight / (heightM * heightM);
//        }

        return BMI;
    }

    /**
     * @param weight
     * @param bodyFat
     * @return
     */
    @Override
    public float calculateFFMI(float weight, float bodyFat) {
        float FFMI = weight * (1 - (bodyFat / 100));
        return FFMI;
    }

    @Override
    public boolean addNewIdStats(int ID) {
        userStats.setIdUserStats(ID);
        Stats.localDatabase.userStatsDAO().addUserStats(userStats);
        return true;
    }

    @Override
    public void getData() {
        List<UserStats> listUsersStats = Stats.localDatabase.userStatsDAO().getUsersStats();

        UserStats getUserStats = new UserStats();

        for (UserStats usrStats : listUsersStats) {
            int id = usrStats.getIdUserStats();
            float weight = usrStats.getWeight();
            float fat = usrStats.getFat();
            float muscle = usrStats.getMuscle();
            float water = usrStats.getWater();
            String dateDetectionWeight = usrStats.getDateWeightDetection();
            String dateDetectionFat = usrStats.getDateFatDetection();
            String dateDetectionMuscle = usrStats.getDateMuscleDetection();
            String dateDetectionWater = usrStats.getDateWaterDetection();


            getUserStats.setIdUserStats(id);
            getUserStats.setWeight(weight);
            getUserStats.setFat(fat);
            getUserStats.setMuscle(muscle);
            getUserStats.setWater(water);
            getUserStats.setDateWeightDetection(dateDetectionWeight);
            getUserStats.setDateFatDetection(dateDetectionFat);
            getUserStats.setDateWaterDetection(dateDetectionWater);
            getUserStats.setDateMuscleDetection(dateDetectionMuscle);
        }

        userStats = getUserStats;
    }


    public void deleteRecordStats(int id) {
        UserStats userStats = new UserStats();
        userStats.setIdUserStats(id);
        Stats.localDatabase.userStatsDAO().deleteUserStats(userStats);
    }

    @Override
    public void updateWeight(int id, float weight, String dateWeightDetection) {
        userStats.setIdUserStats(id);
        userStats.setWeight(weight);
        userStats.setDateWeightDetection(dateWeightDetection);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateFat(int id, float fat, String dateFatDetection) {
        userStats.setIdUserStats(id);
        userStats.setFat(fat);
        userStats.setDateFatDetection(dateFatDetection);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateWater(int id, float water, String dateWaterDetection) {
        userStats.setIdUserStats(id);
        userStats.setWater(water);
        userStats.setDateWaterDetection(dateWaterDetection);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateMuscle(int id, float muscle, String dateMuscleDetection) {
        userStats.setIdUserStats(id);
        userStats.setMuscle(muscle);
        userStats.setDateMuscleDetection(dateMuscleDetection);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void addNewUserStats() {
        Random random = new Random();
        addNewIdStats(random.nextInt());
    }

    @Override
    public boolean checkExistUserStats() {
        if (userStats.getWeight() == NULL_STATS && userStats.getFat() == NULL_STATS &&
                userStats.getMuscle() == NULL_STATS && userStats.getWater() == NULL_STATS) {
            return false;
        }
        return true;
    }

}
