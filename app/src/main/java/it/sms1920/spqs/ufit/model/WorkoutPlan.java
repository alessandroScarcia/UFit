package it.sms1920.spqs.ufit.model;

public class WorkoutPlan {
    private String workoutPlanId;
    private String name;
    private String userOwnerId;
    private int exerciseListId;
    private String trainerId;

    public WorkoutPlan() {
    }

    public String getWorkoutPlanId() {
        return workoutPlanId;
    }

    public String getName() {
        return name;
    }

    public String getUserOwnerId() {
        return userOwnerId;
    }

    public int getExerciseListId() {
        return exerciseListId;
    }

    public String getTrainerId() {
        return trainerId;
    }
}
