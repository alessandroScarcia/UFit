package it.sms1920.spqs.ufit.model.firebase.database;

public class WorkoutPlan {
    private String workoutPlanId;
    private String name;
    private String userOwnerId;
    private String exerciseListId;
    private String trainerId;

    public WorkoutPlan() {
    }

    public WorkoutPlan(String workoutPlanId, String name, String userOwnerId, String exerciseListId, String trainerId) {
        this.workoutPlanId = workoutPlanId;
        this.name = name;
        this.userOwnerId = userOwnerId;
        this.exerciseListId = exerciseListId;
        this.trainerId = trainerId;
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

    public String getExerciseListId() {
        return exerciseListId;
    }

    public String getTrainerId() {
        return trainerId;
    }
}
