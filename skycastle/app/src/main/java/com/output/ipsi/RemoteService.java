package com.output.ipsi;

import com.output.ipsi.ServerData.ServerData;
import com.output.ipsi.ServerData_full.ServerData_full;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteService {
    @GET("/")
    Call<List<ServerData_full>> getUnivData();

    @GET("/")
    Call<List<ServerData>> getMyData(@Query("id") String id);

    @GET("/offline")
    Call<List<ServerData>> getOffData();

}
