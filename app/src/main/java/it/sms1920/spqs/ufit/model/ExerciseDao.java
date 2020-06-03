package it.sms1920.spqs.ufit.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM Exercise")
    List<Exercise> getAllExercises();

    @Insert
    void insertAll(Exercise... exercises);

    @Query("SELECT * FROM Exercise WHERE name LIKE '%' || :key || '%'")
    List<Exercise> getExercisesByName(String key);

    @Query("DELETE FROM Exercise")
    void deleteAll();

}
