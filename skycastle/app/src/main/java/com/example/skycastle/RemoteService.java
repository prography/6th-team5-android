package com.example.skycastle;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RemoteService {
    @GET("/")
    Call<List<UnivData>> getUnivData();

}
