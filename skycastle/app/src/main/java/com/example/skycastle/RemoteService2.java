package com.example.skycastle;

import com.example.skycastle.ServerData.ServerData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RemoteService2 {
    @POST("/?")
    Call<List<ServerData>> getSendData(@Body HashMap<String, String> data);
}
