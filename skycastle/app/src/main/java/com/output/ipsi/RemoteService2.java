package com.output.ipsi;

import com.output.ipsi.ServerData.ServerData;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemoteService2 {
    @POST("/?")
    Call<List<ServerData>> getSendData(@Body HashMap<String, String> data);
}
