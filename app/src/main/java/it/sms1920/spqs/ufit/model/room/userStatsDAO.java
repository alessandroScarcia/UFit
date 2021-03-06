package it.sms1920.spqs.ufit.model.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface userStatsDAO {

    @Insert
    void addUserStats(UserStats userStats);

    @Query("select * from userStats")
    List<UserStats> getUsersStats();

    @Delete
    void deleteUserStats(UserStats userStats);

    @Update
    void updateUserStats(UserStats userStats);

}
