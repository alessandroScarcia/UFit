//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.launcher.userstats;

import android.content.Context;


import java.util.List;
import java.util.Random;

import it.sms1920.spqs.ufit.model.room.Stats;
import it.sms1920.spqs.ufit.model.room.UserStats;

import static java.sql.Types.NULL;


public class StatsPresenter implements iStatsFragment.Presenter {
    private static final float NULL_STATS = 0;
    private Stats statsModel;
    private final iStatsFragment.View view;
    public UserStats userStats;

    public StatsPresenter(iStatsFragment.View view) {
        this.view = view;
    }


    @Override
    public void onTabSelectedAtPosition(int position) {
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

    /**
     * @param ID userStats to add into the database
     */
    @Override
    public void addNewIdStats(int ID) {
        userStats.setIdUserStats(ID);
        Stats.localDatabase.userStatsDAO().addUserStats(userStats);
    }

    /**
     * The function gets all the userStats into the localdatabase and assign all value inside the
     * static User
     */
    @Override
    public void getData() {
        List<UserStats> listUsersStats = Stats.localDatabase.userStatsDAO().getUsersStats();

        UserStats getUserStats = new UserStats();

        for (UserStats usrStats : listUsersStats) {
            //get the values of general stats from the room
            int id = usrStats.getIdUserStats();
            float weight = usrStats.getWeight();
            float fat = usrStats.getFat();
            float muscle = usrStats.getMuscle();
            float water = usrStats.getWater();
            String dateDetectionWeight = usrStats.getDateWeightDetection();
            String dateDetectionFat = usrStats.getDateFatDetection();
            String dateDetectionMuscle = usrStats.getDateMuscleDetection();
            String dateDetectionWater = usrStats.getDateWaterDetection();

            //get the values of muscle stats
            float arm = usrStats.getArmMeasure();
            float chest = usrStats.getChestMeasure();
            float waist = usrStats.getWaistMeasure();
            float tight = usrStats.getTightMeasure();
            float calve = usrStats.getCalveMeasure();
            String dateDetectionArm = usrStats.getDateArmDetection();
            String dateDetectionChest = usrStats.getDateChestDetection();
            String dateDetectionWaist = usrStats.getDateWaistDetection();
            String dateDetectionTight = usrStats.getDateTightDetection();
            String dateDetectionCalve = usrStats.getDateCalveDetection();

            //assign the general stats in scope object
            getUserStats.setIdUserStats(id);
            getUserStats.setWeight(weight);
            getUserStats.setFat(fat);
            getUserStats.setMuscle(muscle);
            getUserStats.setWater(water);
            getUserStats.setDateWeightDetection(dateDetectionWeight);
            getUserStats.setDateFatDetection(dateDetectionFat);
            getUserStats.setDateWaterDetection(dateDetectionWater);
            getUserStats.setDateMuscleDetection(dateDetectionMuscle);

            //assign the muscle stats in scope object
            getUserStats.setArmMeasure(arm);
            getUserStats.setChestMeasure(chest);
            getUserStats.setWaistMeasure(waist);
            getUserStats.setTightMeasure(tight);
            getUserStats.setCalveMeasure(calve);
            getUserStats.setDateArmDetection(dateDetectionArm);
            getUserStats.setDateChestDetection(dateDetectionChest);
            getUserStats.setDateWaistDetection(dateDetectionWaist);
            getUserStats.setDateTightDetection(dateDetectionTight);
            getUserStats.setDateCalveDetection(dateDetectionCalve);

        }

        //copy the general object into the static object
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
            return true;
        }
        return false;
    }

    @Override
    public void updateArm(int idUserStats, float value, String date) {
        userStats.setIdUserStats(idUserStats);
        userStats.setArmMeasure(value);
        userStats.setDateArmDetection(date);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateChest(int idUserStats, float value, String date) {
        userStats.setIdUserStats(idUserStats);
        userStats.setChestMeasure(value);
        userStats.setDateChestDetection(date);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateWaist(int idUserStats, float value, String date) {
        userStats.setIdUserStats(idUserStats);
        userStats.setWaistMeasure(value);
        userStats.setDateWaistDetection(date);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateTight(int idUserStats, float value, String date) {
        userStats.setIdUserStats(idUserStats);
        userStats.setTightMeasure(value);
        userStats.setDateTightDetection(date);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void updateCalve(int idUserStats, float value, String date) {
        userStats.setIdUserStats(idUserStats);
        userStats.setCalveMeasure(value);
        userStats.setDateCalveDetection(date);
        Stats.localDatabase.userStatsDAO().updateUserStats(userStats);
    }

    @Override
    public void setGeneralStats() {
        if (userStats.getWeight() != NULL) {
            view.getWeight().setText(String.valueOf(userStats.getWeight()));
            view.getWeightDate().setText(String.valueOf(userStats.getDateWeightDetection()));
            setBMITextView();
        }

        if (userStats.getFat() != NULL) {
            view.getFat().setText(String.valueOf(userStats.getFat()));
            view.getFatDate().setText(String.valueOf(userStats.getDateFatDetection()));
            setFFMITextView();
        }

        if (userStats.getWater() != NULL) {
            view.getWater().setText(String.valueOf(userStats.getWater()));
            view.getWaterDate().setText(String.valueOf(userStats.getDateWaterDetection()));
        }

        if (userStats.getMuscle() != NULL) {
            view.getMuscle().setText(String.valueOf(userStats.getMuscle()));
            view.getMuscleDate().setText(String.valueOf(userStats.getDateMuscleDetection()));
        }
    }

    @Override
    public void setBodyStats() {
        if (userStats.getArmMeasure() != NULL) {
            view.getArm().setText(String.valueOf(userStats.getArmMeasure()));
            view.getArmDate().setText(String.valueOf(userStats.getDateArmDetection()));
        }

        if (userStats.getChestMeasure() != NULL) {
            view.getChest().setText(String.valueOf(userStats.getChestMeasure()));
            view.getChestDate().setText(String.valueOf(userStats.getDateChestDetection()));

        }

        if (userStats.getWaistMeasure() != NULL) {
            view.getWaist().setText(String.valueOf(userStats.getWaistMeasure()));
            view.getWaistDate().setText(String.valueOf(userStats.getDateWaistDetection()));
        }

        if (userStats.getTightMeasure() != NULL) {
            view.getTight().setText(String.valueOf(userStats.getTightMeasure()));
            view.getTightDate().setText(String.valueOf(userStats.getDateTightDetection()));
        }

        if (userStats.getCalveMeasure() != NULL) {
            view.getCalve().setText(String.valueOf(userStats.getCalveMeasure()));
            view.getCalveDate().setText(String.valueOf(userStats.getDateCalveDetection()));
        }
    }

    public void setFFMITextView() {
        String weightControlValue = view.getWeight().getText().toString();

        //check if weight is a number to calculate the FFMI(if the text view contains a value)
        if (weightControlValue.matches("\\d+(?:\\.\\d+)?")) {
            float FFMIValue = calculateFFMI(Float.parseFloat(view.getWeight().getText().toString()),
                    Float.parseFloat(view.getFat().getText().toString()));
            view.getFFMI().setText(String.valueOf(FFMIValue));
        }
    }

    @Override
    public void initializeDatabase(Context context) {
        //setting database for the session
        setDatabase(context);

        //get data from database
        getData();
    }


    public void setBMITextView() {
        float BMIValue = calculateBMI(Float.parseFloat(view.getWeight().getText().toString()));
        view.getBMI().setText(String.valueOf(BMIValue));
    }


//    public void callRemoveRecordStats(int id) {
//        deleteRecordStats(id);
//    }

    public UserStats getUserStats() {
        return userStats;
    }


}
