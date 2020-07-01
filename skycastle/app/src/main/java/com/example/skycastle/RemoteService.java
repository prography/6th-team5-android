package com.example.skycastle;

import com.example.skycastle.Database.InfoSave;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RemoteService {
    @GET("/susi/")
    Call<List<InfoSave>> getUnivData();
}
