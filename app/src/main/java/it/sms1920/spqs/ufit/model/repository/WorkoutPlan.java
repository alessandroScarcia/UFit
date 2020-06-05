package it.sms1920.spqs.ufit.model.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userOwnerId"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "trainerId",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(
                        name = "userOwnerId_index",
                        value = {"userOwnerId"}
                ),
                @Index(
                        name = "trainerId_index",
                        value = {"trainerId"}
                )
        }
)
public class WorkoutPlan {
    @PrimaryKey(autoGenerate = true)
    private long workoutPlanId;
    @NonNull
    private long userOwnerId;
    private Long trainerId;
    private String name;

    public long getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(long workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(long userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }


}
