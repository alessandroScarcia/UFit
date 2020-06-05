package it.sms1920.spqs.ufit.model.repository;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(
        entities = {Exercise.class, ExerciseTranslation.class, ExerciseWorkoutPlanCrossRef.class,
                Muscle.class, MuscleTranslation.class, User.class, WorkoutPlan.class
        },
        version = 1
)
public abstract class DatabaseHandler extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
}
