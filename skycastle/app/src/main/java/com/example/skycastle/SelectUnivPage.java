package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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
    List<UnivData> univData = new ArrayList<UnivData>();
    ArrayList<UnivList> list = new ArrayList<UnivList>();
    static ArrayList<Univ_ServerSend> univ_serverSends=new ArrayList<Univ_ServerSend>();

    Button button;
    public SharedPreferences prefs;
    boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_univ_page);
        prefs = getSharedPreferences("Pref2", MODE_PRIVATE);
        isFirstRun = prefs.getBoolean("isFirstRun", true);
        setRetrofit();
        button=findViewById(R.id.fin);
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
    private void setRetrofit(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-94-185.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<UnivData>> call = remote.getUnivData();

        call.enqueue(new Callback<List<UnivData>>() {

            @Override
            public void onResponse(Call<List<UnivData>> call, Response<List<UnivData>> response) {
                String test;

                try{
                    univData = response.body();

                    int size=univData.size();
                    for(int i=0; i<size;i++){
                        //Log.d("size", Integer.toString(city_r.getCities().size()));
                         UnivData t_data = univData.get(i);
                        Log.e("name", t_data.getName());
                        Log.e("logo", t_data.getLogo());
                        UnivList univ = new UnivList();
                        univ.setName(t_data.getName());
                        univ.setLogo(t_data.getLogo());

                        ArrayList<String> t_susi_n=new ArrayList<String>();
                        int s_size=t_data.getSusis().size();
                        for(int j=0;j<s_size;j++){
                            Log.d("susis", t_data.susis.get(j).getName());
                            t_susi_n.add(t_data.susis.get(j).getName());
                        }
                        univ.setSusi_n(t_susi_n);

                        ArrayList<String> t_susi_mb=new ArrayList<String>();
                        int sm_size=t_data.getSusi_major_blocks().size();
                        for(int z=0;z<sm_size;z++){
                            Log.d("susis", t_data.susi_major_blocks.get(z).getName());
                            t_susi_mb.add(t_data.susi_major_blocks.get(z).getName());
                        }
                        univ.setSusi_mb(t_susi_mb);

                        ArrayList<String> t_jeongsi_mb=new ArrayList<String>();
                        int j_size=t_data.getJeongsi_major_blocks().size();
                        for(int k=0;k<j_size;k++){
                            t_jeongsi_mb.add(t_data.jeongsi_major_blocks.get(k).getName());
                        }
                        univ.setJeongsi_mb(t_jeongsi_mb);
                        list.add(univ);
                    }

                    recyclerView = (RecyclerView)findViewById(R.id.univ_recycler_view);
                    adapter = new recyclerAdapter(getApplicationContext(), list);

                    layoutManager = new GridLayoutManager(getApplicationContext(), 2);

                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return 1;
                        }
                    });

                    //임시로 데이터 추가
                    /*String[] n_list = {"서울대학교","연세대학교","고려대학교","서강대학교","성균관대학교","한양대학교","중앙대학교","경희대학교","숙명여자대학교"};
                    for(int i=0;i<n_list.length;i++){
                        UnivList univ = new UnivList();
                        univ.setName(n_list[i]);
                        list.add(univ);
                    }*/

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<UnivData>> call, Throwable t) {

            }
        });
    }

    private void setRetrofit2(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-94-185.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<UnivData>> call = remote.getUnivData();

        call.enqueue(new Callback<List<UnivData>>() {

            @Override
            public void onResponse(Call<List<UnivData>> call, Response<List<UnivData>> response) {
                String test;

                try{
                    univData = response.body();

                    int size=univData.size();
                    for(int i=0; i<size;i++){
                        //Log.d("size", Integer.toString(city_r.getCities().size()));
                        UnivData t_data = univData.get(i);
                        Log.e("name", t_data.getName());
                        Log.e("logo", t_data.getLogo());
                        UnivList univ = new UnivList();
                        univ.setName(t_data.getName());
                        univ.setLogo(t_data.getLogo());

                        ArrayList<String> t_susi_n=new ArrayList<String>();
                        int s_size=t_data.getSusis().size();
                        for(int j=0;j<s_size;j++){
                            Log.d("susis", t_data.susis.get(j).getName());
                            t_susi_n.add(t_data.susis.get(j).getName());
                        }
                        univ.setSusi_n(t_susi_n);

                        ArrayList<String> t_susi_mb=new ArrayList<String>();
                        int sm_size=t_data.getSusi_major_blocks().size();
                        for(int z=0;z<sm_size;z++){
                            Log.d("susis", t_data.susi_major_blocks.get(z).getName());
                            t_susi_mb.add(t_data.susi_major_blocks.get(z).getName());
                        }
                        univ.setSusi_mb(t_susi_mb);

                        ArrayList<String> t_jeongsi_mb=new ArrayList<String>();
                        int j_size=t_data.getJeongsi_major_blocks().size();
                        for(int k=0;k<j_size;k++){
                            t_jeongsi_mb.add(t_data.jeongsi_major_blocks.get(k).getName());
                        }
                        univ.setJeongsi_mb(t_jeongsi_mb);
                        list.add(univ);
                    }

                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<UnivData>> call, Throwable t) {

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
