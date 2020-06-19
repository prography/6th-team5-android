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

    @Query("SELECT * FROM InfoSave WHERE start_date LIKE :search")
    public List<InfoSave> findListWithCity(String search);

    @Insert
    void insert(InfoSave infoSave);

    @Update
    void update(InfoSave infoSave);

    @Delete
    void delete(InfoSave infoSave);

    @Query("DELETE FROM InfoSave")
    void deleteAll();

    @Query("DELETE FROM InfoSave WHERE univ= :city_d")
    void erase(String city_d);
}
