package com.output.ipsi.Offline;

import android.icu.text.SimpleDateFormat;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.DataEvent_selected;
import com.output.ipsi.R;
import com.output.ipsi.RemoteService;
import com.output.ipsi.ServerData.ServerData;
import com.output.ipsi.ServerData.jhs;
import com.output.ipsi.ServerData.majors;
import com.output.ipsi.ServerData.schdules;
import com.output.ipsi.UnivReveiw.ReviewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfflineFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    List<ServerData> offunivData = new ArrayList<ServerData>();
    List<ServerData> offData = new ArrayList<ServerData>();
    Set<Integer> set = new HashSet<Integer>();
    List<Integer> offdate_list;
    Offline_recyclerview offline_recyclerview;
    Off_select_recyclerview off_select_recyclerview;
    String univ="";
    String jh="";
    String major="";
    String type="";
    ArrayList<jhs> jhs;
    ArrayList<majors> majors;
    ArrayList<schdules> schedules;
    ArrayList<String> selected_texts=new ArrayList<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(DataEvent_offline event){
        this.type=event.type;
        if(type.equals("name")){
            univ=event.eventText;
            set_jh();
        }else if(type.equals("jhs")){
            jh=event.eventText;
            set_major();
        }else if(type.equals("majors")){
            major=event.eventText;

        }
    }

    public void set_jh(){
        for(int i=0;i<offData.size();i++){
            if(offData.get(i).getName().equals(univ)){
                jhs=offData.get(i).getSjs().get(0).getJhs();
                List<String> off_univs=new ArrayList<>();
                for(int j=0;j<jhs.size();j++){
                    off_univs.add(jhs.get(j).getName());
                }
                set_select_recyclerview(off_univs,"jhs");
            }
        }
    }

    public void set_major(){
        for(int i=0;i<jhs.size();i++){
            if(jhs.get(i).getName().equals(jh)){
                majors=jhs.get(i).getMajors();
                List<String> off_univs=new ArrayList<>();
                for(int j=0;j<majors.size();j++){
                    off_univs.add(majors.get(j).getName());
                }
                set_select_recyclerview(off_univs,"majors");
            }
        }
    }

    public void set_choice(){
        for(int i=0;i<majors.size();i++){
            if(majors.get(i).getName().equals(major)){
                schedules=majors.get(i).getSchedules();
            }
        }
        String selected_text=univ+" "+jh+" "+major+" "+schedules.get(0).getDescription();
        selected_texts.add(selected_text);

    }

    public void set_select_recyclerview(List<String> re_list, String type){
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        off_select_recyclerview = new Off_select_recyclerview(getContext(), re_list,type);
        recyclerView2.setAdapter(off_select_recyclerview);
    }

    public OfflineFragment(List<ServerData> univData) {
        //this.offunivData = univData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_offline, container, false);
        setHasOptionsMenu(true);
        try{
            EventBus.getDefault().register(this);
        }catch(Exception e){}
        recyclerView = (RecyclerView) rootView.findViewById(R.id.offline_re);
        recyclerView2=(RecyclerView) rootView.findViewById(R.id.choiceView);
        setRetrofit();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    private void setRetrofit() {

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-101-171.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<ServerData>> call = remote.getOffData();

        call.enqueue(new Callback<List<ServerData>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<ServerData>> call, Response<List<ServerData>> response) {
                String test;

                try {
                    offData = response.body();
                    //Log.d("univdata", univData.get(0).getDescription());

                    int size = offData.size();
                    RemoveDup();
                    Log.d("checkoff", Integer.toString(offdate_list.get(0)));

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    offline_recyclerview = new Offline_recyclerview(getContext(), offData,offdate_list);
                    recyclerView.setAdapter(offline_recyclerview);

                    //아래 리사이클러뷰
                    List<String> off_univs=new ArrayList<>();
                    for(int i=0;i<offData.size();i++){
                        off_univs.add(offData.get(i).getName());
                    }
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutManager2);
                    recyclerView2.setHasFixedSize(true);
                    off_select_recyclerview = new Off_select_recyclerview(getContext(), off_univs,"name");
                    recyclerView2.setAdapter(off_select_recyclerview);
                } catch (Exception e){
                Log.d("onResponse", "Error");
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<List<ServerData>> call, Throwable t) {

        }
    });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void RemoveDup(){
        int size = offData.size();
        for(int i=0;i<offData.size();i++) {
            for (int j = 0; j < offData.get(i).getSjs().size(); j++) {
                for (int k = 0; k < offData.get(i).getSjs().get(j).getJhs().size(); k++) {
                    for (int l = 0; l < offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size(); l++) {
                        for(int m=0;m<offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                            schdules scd=offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                            String[] targetSlicing = scd.getStart_date().split("-");
                            String[] targetSlicing2 = scd.getEnd_date().split("-");
                            if(scd.getStart_date().equals(scd.getEnd_date())){
                                int offdate=0;
                                offdate=offdate/*+Integer.parseInt(targetSlicing[0])*10000*/
                                        +Integer.parseInt(targetSlicing[1])*100+Integer.parseInt(targetSlicing[2]);
                                set.add(offdate);
                            }else{
                                String date1 = scd.getStart_date();
                                String date2 = scd.getEnd_date();
                                try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                                    // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
                                    Date FirstDate = format.parse(date1);
                                    Date SecondDate = format.parse(date2);

                                    // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
                                    // 연산결과 -950400000. long type 으로 return 된다.
                                    long calDate = FirstDate.getTime() - SecondDate.getTime();

                                    // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
                                    // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
                                    long calDateDays = calDate / ( 24*60*60*1000);

                                    calDateDays = Math.abs(calDateDays);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(FirstDate);
                                    for(long n=0;n<calDateDays;n++){
                                        cal.add(Calendar.DATE,1);
                                        String[] targetSlicing3 = format.format(cal.getTime()).split("-");
                                        int offdate=0;
                                        offdate=offdate/*+Integer.parseInt(targetSlicing[0])*10000*/
                                                +Integer.parseInt(targetSlicing3[1])*100+Integer.parseInt(targetSlicing3[2]);
                                        set.add(offdate);
                                    }
                                }
                                catch(ParseException e)
                                {
                                    Log.d("error","parseError");
                                }
                            }

                        }
                    }
                }
            }
        }
        offdate_list=new ArrayList<Integer>(set);
        Collections.sort(offdate_list);
        Log.d("offdata_size", Integer.toString(offdate_list.get(offdate_list.size()-1)));
    }
}