package com.example.skycastle.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InfoSave.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract InfoSaveDao infoSaveDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class , "infosave-db")
                    //.allowMainThreadQueries()
                    //.fallbackToDestructiveMigration()
                    .build();
        }
        return  INSTANCE;}

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

