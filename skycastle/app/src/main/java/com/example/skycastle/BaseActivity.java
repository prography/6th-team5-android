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

import com.applandeo.materialcalendarview.EventDay;
import com.example.skycastle.Calendar.DayClass;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ReviewFragment reviewFragment;
    List<ServerData> univData = new ArrayList<ServerData>();
    List<ServerData> LikeData=new ArrayList<ServerData>();
    String android_id;
    Map<Calendar,List<DayClass>> eventMap = new HashMap<>();
    List<EventDay> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Intent intent_loading = new Intent(this, LoadingActivity.class);
        startActivity(intent_loading);
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

                    for(int i=0;i<univData.size();i++){
                        for(int j=0;j<univData.get(i).getSjs().size();j++){
                            for(int k=0;k<univData.get(i).getSjs().get(j).getJhs().size();k++){
                                for(int l=0;l<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size();l++){
                                    int check=0;
                                    for(int m=0;m<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                                        schdules scd=univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                                        //start_date
                                        if(scd.getStart_date()!=null){
                                            String[] targetSlicing = scd.getStart_date().split("-");
                                            int dYear = Integer.parseInt(targetSlicing[0]);
                                            int dMonth = Integer.parseInt(targetSlicing[1]);
                                            int dDay = Integer.parseInt(targetSlicing[2]);
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.set(dYear, dMonth-1, dDay);
                                            EventDay ed=new EventDay(calendar, R.drawable.cal_icon);
                                            DayClass dc=new DayClass();
                                            String t1=univData.get(i).getName()+" "
                                                    +univData.get(i).getSjs().get(j).getSj()+" "
                                                    +univData.get(i).getSjs().get(j).getJhs().get(k).getName()
                                                    +univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName();
                                            dc.setUniv_info(t1);
                                            dc.setLogo(univData.get(i).getLogo());
                                            dc.setDescription(scd.getDescription());
                                            dc.setHour(targetSlicing[3]);
                                            dc.setMinute(targetSlicing[4]);
                                            dc.setS_or_e(0);
                                            if(eventMap.get(calendar)==null){
                                                List<DayClass> dc_list=new ArrayList<DayClass>();
                                                dc_list.add(dc);
                                                eventMap.put(calendar,dc_list);
                                                events.add(ed);
                                            }else{
                                                eventMap.get(calendar).add(dc);
                                                //events.add(ed);
                                            }
                                        }

                                        //end_date
                                        Log.d("end_Date",scd.getEnd_date());
                                        if(scd.getEnd_date()!=null) {
                                            String[] targetSlicing2 = scd.getEnd_date().split("-");
                                            int dYear = Integer.parseInt(targetSlicing2[0]);
                                            int dMonth = Integer.parseInt(targetSlicing2[1]);
                                            int dDay = Integer.parseInt(targetSlicing2[2]);
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.set(dYear, dMonth - 1, dDay);
                                            EventDay ed = new EventDay(calendar, R.drawable.cal_icon);

                                            String t2 = univData.get(i).getName() + " "
                                                    + univData.get(i).getSjs().get(j).getSj() + " "
                                                    + univData.get(i).getSjs().get(j).getJhs().get(k).getName() + " "
                                                    + univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName();
                                            DayClass dc=new DayClass();
                                            dc.setUniv_info(t2);
                                            dc.setLogo(univData.get(i).getLogo());
                                            dc.setDescription(scd.getDescription());
                                            dc.setHour(targetSlicing2[3]);
                                            dc.setMinute(targetSlicing2[4]);
                                            dc.setS_or_e(1);

                                            if(eventMap.get(calendar)==null){
                                                List<DayClass> dc_list=new ArrayList<DayClass>();
                                                dc_list.add(dc);
                                                eventMap.put(calendar,dc_list);
                                                events.add(ed);
                                            }else{
                                                eventMap.get(calendar).add(dc);
                                                //events.add(ed);
                                            }//시작일이랑 끝이랑 아이콘 다르게
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Calendar calendar = Calendar.getInstance();
                    EventDay ed=new EventDay(calendar, R.drawable.cal_icon);
                    if(eventMap.get(calendar)==null){
                        events.add(ed);
                    }
                    calendarFragment=new CalendarFragment(univData,eventMap,events);
                    reviewFragment = new ReviewFragment(univData);

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
