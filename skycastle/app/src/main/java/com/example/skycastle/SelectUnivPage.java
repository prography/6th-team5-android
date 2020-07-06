package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.skycastle.MyDatabase.AppDatabase;
import com.example.skycastle.MyDatabase.InfoSave;
import com.example.skycastle.MyDatabase.univ_img;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.UnivSchdule.UnivSchdule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectUnivPage extends AppCompatActivity {
    RecyclerView recyclerView;
    recyclerAdapter adapter;
    GridLayoutManager layoutManager;
    List<InfoSave> univData = new ArrayList<InfoSave>();
    List<ServerData> univSchdules = new ArrayList<ServerData>();
    List<ServerData> list = new ArrayList<ServerData>();
    static ArrayList<Univ_ServerSend> univ_serverSends=new ArrayList<Univ_ServerSend>();
    Thread thread;
    Button button;
    public SharedPreferences prefs;
    boolean isFirstRun;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_univ_page);
        prefs = getSharedPreferences("Pref2", MODE_PRIVATE);
        isFirstRun = prefs.getBoolean("isFirstRun", true);
        button=findViewById(R.id.fin);

        android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        //android_id="123";
        Log.d("Android_ID >>> ", android_id);

        Intent intent = getIntent();
        list=(List<ServerData>) intent.getSerializableExtra("univ_n");
        Log.d("check1", list.get(0).getName());
        //Log.d("intent",list.get(0).getSj());

        final AppDatabase db=AppDatabase.getAppDatabase(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.univ_recycler_view);
        adapter = new recyclerAdapter(getApplicationContext(), list,db);

        layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFirstRun();
                setRetrofit2();
                Intent intent=new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(intent);
            }
        });
        if (isFirstRun==false){
            finish();
        }
    }


    private void setRetrofit2(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://125.130.100.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");
        Map map=new HashMap();

        String send_t=android_id;

        map.put("id",send_t);
        for(int i=0; i<univ_serverSends.size();i++){
            Univ_ServerSend t_data=univ_serverSends.get(i);
                map.put("univ"+i,t_data.getUniv_n());
                map.put("sj"+i,t_data.getSj());
                map.put("jh"+i,t_data.getJh());
                map.put("block"+i,t_data.getMajor());
        }
        Log.d("text", send_t);
        final RemoteService2 remote = retrofit.create(RemoteService2.class);

        Call<List<ServerData>> call = remote.getSendData(map);
        Log.d("onResponse", "3");
        call.enqueue(new Callback<List<ServerData>>() {

            @Override
            public void onResponse(Call<List<ServerData>> call, Response<List<ServerData>> response) {
                String test;
                if (!response.isSuccessful()) {
                    Log.d("code",Integer.toString(response.code()));
                    return;
                }
                try{
                    univSchdules = response.body();

                    int size=univSchdules.size();
                    Log.d("onResponse", "4");

                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ServerData>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    public void checkFirstRun() {

        if (isFirstRun) {
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }

    }
}
