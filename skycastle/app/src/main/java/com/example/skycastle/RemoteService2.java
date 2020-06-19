package com.example.skycastle;

import com.example.skycastle.UnivSchdule.UnivSchdule;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RemoteService2 {
    @GET("/selectall/?")
    Call<List<UnivSchdule>> getSendData(@QueryMap Map<String, String> data);
}
