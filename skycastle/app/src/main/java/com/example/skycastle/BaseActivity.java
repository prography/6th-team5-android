package com.example.skycastle;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skycastle.Home.HomeAdapter;
import com.example.skycastle.Home.HomeItem;
import com.example.skycastle.R;
import com.example.skycastle.Calendar.CalendarFragment;
import com.example.skycastle.Home.HomeFragment;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.ServerData.jhs;
import com.example.skycastle.ServerData.schdules;
import com.example.skycastle.ServerData.sjs;
import com.example.skycastle.UnivReveiw.ReviewFragment;
import com.example.skycastle.UnivReveiw.WebViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private ReviewFragment reviewFragment = new ReviewFragment();
    List<ServerData> univData = new ArrayList<ServerData>();
    List<ServerData> LikeData=new ArrayList<ServerData>();
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        // Setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRetrofit();

        // Init View setting

    }

    private class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    fragmentTransaction.replace(R.id.main_container, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_calendar:
                    fragmentTransaction.replace(R.id.main_container, calendarFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_review:
                    fragmentTransaction.replace(R.id.main_container, reviewFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }

    private void setRetrofit(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-101-171.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<ServerData>> call = remote.getMyData(android_id);

        call.enqueue(new Callback<List<ServerData>>() {

            @Override
            public void onResponse(Call<List<ServerData>> call, Response<List<ServerData>> response) {
                String test;

                try{
                    univData = response.body();
                    //Log.d("univdata", univData.get(0).getDescription());

                    int size=univData.size();
                    Log.d("check1", univData.get(0).getName());


                    homeFragment=new HomeFragment(univData);
                    calendarFragment=new CalendarFragment(univData);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, homeFragment).commitAllowingStateLoss();

                    bottomNavigationView = findViewById(R.id.bottom_navigation);
                    bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

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

}
