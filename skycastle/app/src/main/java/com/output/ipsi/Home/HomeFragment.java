package com.output.ipsi.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.LoadingActivity;
import com.output.ipsi.R;
import com.output.ipsi.RemoteService;
import com.output.ipsi.SelectUnivPage;
import com.output.ipsi.ServerData.ServerData;
import com.output.ipsi.ServerData.schdules;
import com.output.ipsi.ServerData_full.ServerData_full;
import com.output.ipsi.SettingFragment;
import com.output.ipsi.BaseActivity;
import com.output.ipsi.Univ_ServerSend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    List<ServerData> myunivData = new ArrayList<ServerData>();
    ArrayList<Univ_ServerSend> my_univ_serverSends=new ArrayList<>();

    public HomeFragment(List<ServerData> univData){
        this.myunivData=univData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.home_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        List<HomeItem> data = new ArrayList<>();

        for(int i=0;i<myunivData.size();i++){
            for(int j=0;j<myunivData.get(i).getSjs().size();j++){
                for(int k=0;k<myunivData.get(i).getSjs().get(j).getJhs().size();k++){
                    for(int l=0;l<myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size();l++){
                        int check=0;
                        Univ_ServerSend serversend_d=new Univ_ServerSend();
                        serversend_d.setUniv_n(myunivData.get(i).getName());
                        serversend_d.setSj(myunivData.get(i).getSjs().get(j).getSj());
                        serversend_d.setJh(myunivData.get(i).getSjs().get(j).getJhs().get(k).getName());
                        serversend_d.setMajor(myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName());
                        my_univ_serverSends.add(serversend_d);
                        HomeItem headitem=null;
                        List<HomeItem> childList=new ArrayList<>();
                        for(int m=0;m<myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                            schdules scd=myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                            if(scd.getIs_valid()==1){
                                if(check==0){
                                    headitem=new HomeItem(HomeAdapter.HEADER, myunivData.get(i).getLogo(),myunivData.get(i).getName()
                                            +" "+scd.getDescription(),
                                            myunivData.get(i).getSjs().get(j).getSj()+" "+myunivData.get(i).getSjs().get(j).getJhs().get(k).getName(),
                                            scd.getStart_date(),scd.getEnd_date(),myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName());
                                    data.add(headitem);

                                    HomeItem childitem=new HomeItem(HomeAdapter.CHILD, myunivData.get(i).getLogo(),myunivData.get(i).getName(),
                                            scd.getDescription(),
                                            scd.getStart_date(),scd.getEnd_date(),myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName());
                                    //data.add(childitem);
                                    childList.add(childitem);
                                    check=1;
                                }else{
                                    HomeItem childitem=new HomeItem(HomeAdapter.CHILD, myunivData.get(i).getLogo(),myunivData.get(i).getName(),
                                            scd.getDescription(),
                                            scd.getStart_date(),scd.getEnd_date(),myunivData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName());
                                    //data.add(childitem);
                                    childList.add(childitem);
                                }
                            }
                            Log.d("test",scd.getEnd_date());
                            //if(scd.get)
                        }headitem.setInvisibleChildren(childList);
                    }
                }
            }
        }

        homeAdapter = new HomeAdapter(getContext(), data);

        recyclerView.setAdapter(homeAdapter);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fg_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_add:
                //Toast.makeText(getActivity(), "menu_home_add", Toast.LENGTH_SHORT).show();
                Intent intent_loading = new Intent(getActivity(), LoadingActivity.class);
                startActivity(intent_loading);
                setRetrofit();
                break;
            case R.id.menu_home_remove:
                Intent intent_loading2 = new Intent(getActivity(), LoadingActivity.class);
                startActivity(intent_loading2);
                setRetrofit();
                break;
            case R.id.menu_home_setting:
                //Toast.makeText(getActivity(), "menu_home_setting", Toast.LENGTH_SHORT).show();
                ((BaseActivity)getActivity()).replaceFragment(new SettingFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLimitAlterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("경고창").setMessage("6개 이상 추가할 수 없습니다.");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setRetrofit(){

        Log.d("onResponse", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-101-171.ap-northeast-2.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<ServerData_full>> call = remote.getUnivData();

        call.enqueue(new Callback<List<ServerData_full>>() {

            @Override
            public void onResponse(Call<List<ServerData_full>> call, Response<List<ServerData_full>> response) {
                String test;

                try{
                    List<ServerData_full> univData_full = response.body();
                    //Log.d("univdata", univData.get(0).getDescription());

                    int size=univData_full.size();
                    Log.d("check1", univData_full.get(0).getName());

                    Intent intent=new Intent(getActivity(), SelectUnivPage.class);
                    intent.putExtra("univ_n", (Serializable) univData_full);
                    intent.putExtra("univ_selected",(Serializable) my_univ_serverSends);
                    startActivity(intent);
                    getActivity().finish();

                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ServerData_full>> call, Throwable t) {

            }
        });
    }
}
