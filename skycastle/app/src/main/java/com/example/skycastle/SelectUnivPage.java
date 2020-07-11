package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.skycastle.MyDatabase.AppDatabase;
import com.example.skycastle.MyDatabase.InfoSave;
import com.example.skycastle.MyDatabase.univ_img;
import com.example.skycastle.ServerData.ServerData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    ArrayList<String> selected_list=new ArrayList<String>();
    Thread thread;
    Button button;
    public SharedPreferences prefs;
    boolean isFirstRun;
    String android_id;
    String token;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(DataEvent_selected event){
        selected_list.add(event.eventText);
        RecyclerView mRecyclerView2 = findViewById(R.id.select_recycler);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(SelectUnivPage.this);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        recyclerAdapter_selected list_adapter2 = new recyclerAdapter_selected(SelectUnivPage.this, selected_list);
        mRecyclerView2.setAdapter(list_adapter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_univ_page);


        try {
            //FirebaseApp.initializeApp(this);
            token = FirebaseInstanceId.getInstance().getToken();
            Log.d("IDService","device token : "+token);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try{
            EventBus.getDefault().register(this);
        }catch(Exception e){}

        prefs = getSharedPreferences("Pref2", MODE_PRIVATE);
        isFirstRun = prefs.getBoolean("isFirstRun", true);
        button=findViewById(R.id.fin);

        android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        //android_id="123";
        Log.d("Android_ID >>> ", android_id);

        Intent intent = getIntent();
        list=(List<ServerData>) intent.getSerializableExtra("univ_n");
        Log.d("check1", list.get(0).getName());
        ArrayList<Univ_ServerSend> temp_serverSends=(ArrayList<Univ_ServerSend>) intent.getSerializableExtra("univ_selected");

        if(temp_serverSends!=null){
            univ_serverSends=temp_serverSends;
            for(int i=0;i<univ_serverSends.size();i++){
                String temp=univ_serverSends.get(i).getUniv_n()+" "+univ_serverSends.get(i).getSj()+" "
                        +univ_serverSends.get(i).getJh()+" "+univ_serverSends.get(i).getMajor();
                selected_list.add(temp);
            }
        }

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

        RecyclerView mRecyclerView2 = findViewById(R.id.select_recycler);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(SelectUnivPage.this);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        recyclerAdapter_selected list_adapter2 = new recyclerAdapter_selected(SelectUnivPage.this, selected_list);
        mRecyclerView2.setAdapter(list_adapter2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFirstRun();
                setRetrofit2();
                Intent intent=new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(intent);
                selected_list=null;
                finish();
            }
        });
        Log.d("checkfirstrun",Boolean.toString(isFirstRun));
        /*if (!isFirstRun){
            finish();
        }*/
    }


    private void setRetrofit2(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-101-171.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");
        HashMap<String,String> datamap=new HashMap<>();

        String send_t=android_id;

        datamap.put("id",send_t);
        datamap.put("token",token);
        datamap.put("num",Integer.toString(univ_serverSends.size()));
        for(int i=0; i<univ_serverSends.size();i++){
            Univ_ServerSend t_data=univ_serverSends.get(i);
                datamap.put("univ"+i,t_data.getUniv_n());
                datamap.put("sj"+i,t_data.getSj());
                datamap.put("jh"+i,t_data.getJh());
                datamap.put("major"+i,t_data.getMajor());
        }
        Log.d("text", send_t);
        final RemoteService2 remote = retrofit.create(RemoteService2.class);

        Call<List<ServerData>> call = remote.getSendData(datamap);

        Log.d("onResponse",call.toString());
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            EventBus.getDefault().unregister(this);
        }catch (Exception e){}
    }
}
