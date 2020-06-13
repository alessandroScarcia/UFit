package it.sms1920.spqs.ufit.contract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.sms1920.spqs.ufit.model.User;
import it.sms1920.spqs.ufit.model.UserStats;

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
