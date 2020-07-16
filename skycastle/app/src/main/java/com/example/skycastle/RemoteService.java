package com.example.skycastle;

import com.example.skycastle.MyDatabase.InfoSave;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.ServerData_full.ServerData_full;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteService {
    @GET("/")
    Call<List<ServerData_full>> getUnivData();

    @GET("/")
    Call<List<ServerData>> getMyData(@Query("id") String id);
}
