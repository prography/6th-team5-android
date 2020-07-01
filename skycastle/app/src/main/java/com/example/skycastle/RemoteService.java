package com.example.skycastle;

import com.example.skycastle.MyDatabase.InfoSave;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemoteService {
    @GET("/susi/")
    Call<List<InfoSave>> getUnivData();
}
