package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.skycastle.MyDatabase.AppDatabase;
import com.example.skycastle.MyDatabase.InfoSave;
import com.example.skycastle.MyDatabase.univ_img;
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
    List<UnivSchdule> univSchdules = new ArrayList<UnivSchdule>();
    List<univ_img> list = new ArrayList<univ_img>();
    static ArrayList<Univ_ServerSend> univ_serverSends=new ArrayList<Univ_ServerSend>();
    Thread thread;
    Button button;
    public SharedPreferences prefs;
    boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_univ_page);
        prefs = getSharedPreferences("Pref2", MODE_PRIVATE);
        isFirstRun = prefs.getBoolean("isFirstRun", true);
        button=findViewById(R.id.fin);

        Intent intent = getIntent();
        list=(List<univ_img>) intent.getSerializableExtra("univ_n");
        Log.d("intent",list.get(0).getSj());

        recyclerView = (RecyclerView)findViewById(R.id.univ_recycler_view);
        adapter = new recyclerAdapter(getApplicationContext(), list);

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
                .baseUrl("http://ec2-54-180-94-185.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");
        Map map=new HashMap();

        String send_t=Integer.toString(univ_serverSends.size());
        map.put("num",send_t);
        for(int i=0; i<univ_serverSends.size();i++){
            Univ_ServerSend t_data=univ_serverSends.get(i);
            if(univ_serverSends.get(i).getSj()=="수시"){
                send_t=send_t+"&univ"+i+"="+t_data.getUniv_n()+"&sj"+i+"=수시&jh"+i+"="+t_data.getSusi_j()
                        +"&block"+i+"="+t_data.getS_block();
                map.put("univ"+i,t_data.getUniv_n());
                map.put("sj"+i,"수시");
                map.put("jh"+i,t_data.getSusi_j());
                map.put("block"+i,t_data.getS_block());
            }else{
                send_t=send_t+"&univ"+i+"="+t_data.getUniv_n()+"&sj"+i+"=정시&gun"+i+"="+t_data.getGun()
                        +"&block"+i+"="+t_data.getJ_block();
                map.put("univ"+i,t_data.getUniv_n());
                map.put("sj"+i,"정시");
                map.put("gun"+i,t_data.getGun());
                map.put("block"+i,t_data.getJ_block());
            }
        }
        Log.d("text", send_t);
        final RemoteService2 remote = retrofit.create(RemoteService2.class);

        Call<List<UnivSchdule>> call = remote.getSendData(map);
        Log.d("onResponse", "3");
        call.enqueue(new Callback<List<UnivSchdule>>() {

            @Override
            public void onResponse(Call<List<UnivSchdule>> call, Response<List<UnivSchdule>> response) {
                String test;

                try{
                    univSchdules = response.body();

                    int size=univSchdules.size();
                    Log.d("onResponse", "4");
                    final ArrayList<InfoSave> infoSaves=new ArrayList<InfoSave>();
                    /*for(int i=0;i<size;i++){
                        String univ=univSchdules.get(i).getUniv();
                        String sj=univSchdules.get(i).getSj();
                        String jh=univSchdules.get(i).getJh();
                        String block=univSchdules.get(i).getBlock();
                        ArrayList<schedules> schedules=univSchdules.get(i).getSchedules();
                        for(int j=0;j<schedules.size();j++) {
                            InfoSave infoSave = new InfoSave();
                            String description = schedules.get(j).getDescription();
                            String start_date = schedules.get(j).getStart_date();
                            String end_date = schedules.get(j).getEnd_date();
                            infoSave.setUniv(univ);
                            infoSave.setSj(sj);
                            infoSave.setJh(jh);
                            infoSave.setBlock(block);
                            infoSave.setDescription(description);
                            infoSave.setStart_date(start_date);
                            infoSave.setEnd_date(end_date);
                            infoSaves.add(infoSave);
                            Log.d("description", infoSave.getDescription());
                            Log.d("start_date", start_date);
                            Log.d("end_date", end_date);
                        }
                    }*/
                    final AppDatabase db=AppDatabase.getAppDatabase(getApplicationContext());

                    Runnable runnable1=new Runnable() {
                        @Override
                        public void run() {
                            for(int k=0;k<infoSaves.size();k++){
                                //f_list=db.infoSaveDao().findListWithCity(infoSaves.get(k));
                            }
                        }
                    };
                    thread = new Thread(runnable1);
                    thread.start();

                    Runnable runnable2=new Runnable() {
                        @Override
                        public void run() {
                            for(int j=0;j<infoSaves.size();j++){
                                //db.infoSaveDao().insert(infoSaves.get(j));
                            }
                        }
                    };
                    thread = new Thread(runnable2);
                    thread.start();
                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<UnivSchdule>> call, Throwable t) {

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
