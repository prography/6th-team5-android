package com.output.ipsi.Offline;

import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.DataEvent_selected;
import com.output.ipsi.R;
import com.output.ipsi.RemoteService;
import com.output.ipsi.ServerData.ServerData;
import com.output.ipsi.ServerData.schdules;
import com.output.ipsi.UnivReveiw.ReviewAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(DataEvent_offline event){

    }

    public OfflineFragment(List<ServerData> univData) {
        this.offunivData = univData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_offline, container, false);
        setHasOptionsMenu(true);
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
                    for(int i=0;i<offunivData.size();i++){
                        off_univs.add(offunivData.get(i).getName());
                    }
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(layoutManager2);
                    recyclerView2.setHasFixedSize(true);
                    off_select_recyclerview = new Off_select_recyclerview(getContext(), off_univs,"name");
                    recyclerView2.setAdapter(offline_recyclerview);
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

    public void RemoveDup(){
        int size = offData.size();
        for(int i=0;i<offData.size();i++) {
            for (int j = 0; j < offData.get(i).getSjs().size(); j++) {
                for (int k = 0; k < offData.get(i).getSjs().get(j).getJhs().size(); k++) {
                    for (int l = 0; l < offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size(); l++) {
                        for(int m=0;m<offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                            schdules scd=offData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                            String[] targetSlicing = scd.getStart_date().split("-");
                            int offdate=0;
                            offdate=offdate/*+Integer.parseInt(targetSlicing[0])*10000*/
                            +Integer.parseInt(targetSlicing[1])*100+Integer.parseInt(targetSlicing[2]);
                            set.add(offdate);
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