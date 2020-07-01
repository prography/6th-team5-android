package com.example.skycastle.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InfoSaveDao {

    @Query("SELECT * FROM InfoSave")
    LiveData<List<InfoSave>> getAll();

    @Query("SELECT DISTINCT university FROM InfoSave")
    public List<String> findUniv();

    @Insert
    void insert(List<InfoSave> infoSaves);

    @Update
    void update(InfoSave infoSave);

    @Delete
    void delete(InfoSave infoSave);

    @Query("DELETE FROM InfoSave")
    void deleteAll();

    @Query("DELETE FROM InfoSave WHERE university= :city_d")
    void erase(String city_d);
}
