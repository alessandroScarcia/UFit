package it.sms1920.spqs.ufit.model.repository;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        primaryKeys = {"exerciseId", "workoutPlanId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "exerciseId",
                        childColumns = "exerciseId",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = WorkoutPlan.class,
                        parentColumns = "workoutPlanId",
                        childColumns = "workoutPlanId",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(
                        name = "exerciseId_index",
                        value = {"exerciseId"}
                ),
                @Index(
                        name = "workoutPlanId_index",
                        value = {"workoutPlanId"}
                )
        }
)
public class ExerciseWorkoutPlanCrossRef {
    private long exerciseId;
    private long workoutPlanId;

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(long workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }
}
