package com.example.skycastle.MyDatabase;

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

    @Query("SELECT DISTINCT university, sj FROM InfoSave")
    public List<univ_img> findUniv();

    @Query("SELECT DISTINCT university, sj, jh FROM InfoSave WHERE university LIKE :name")
    public List<JhData> findJhData(String name);

    @Query("SELECT DISTINCT university, sj, block FROM InfoSave WHERE university LIKE :name")
    public List<BlockData> findBlokcData(String name);

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
