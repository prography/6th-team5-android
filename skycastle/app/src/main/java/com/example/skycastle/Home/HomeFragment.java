package com.example.skycastle.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.R;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.ServerData.schdules;
import com.example.skycastle.SettingFragment;
import com.example.skycastle.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    List<ServerData> univData = new ArrayList<ServerData>();

    public HomeFragment(List<ServerData> univData){
        this.univData=univData;
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

        for(int i=0;i<univData.size();i++){
            for(int j=0;j<univData.get(i).getSjs().size();j++){
                for(int k=0;k<univData.get(i).getSjs().get(j).getJhs().size();k++){
                    for(int l=0;l<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size();l++){
                        int check=0;
                        for(int m=0;m<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                            schdules scd=univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                            if(scd.getIs_valid()==1){
                                if(check==0){
                                    data.add(new HomeItem(HomeAdapter.HEADER, univData.get(i).getLogo(),univData.get(i).getName()
                                            +" "+scd.getDescription(),
                                            univData.get(i).getSjs().get(j).getSj()+" "+univData.get(i).getSjs().get(j).getJhs().get(k).getName(),
                                            scd.getStart_date()));
                                    check=1;
                                }else{
                                    data.add(new HomeItem(HomeAdapter.CHILD, univData.get(i).getLogo(),univData.get(i).getName(),
                                            scd.getDescription(),
                                            scd.getStart_date()));
                                }
                            }
                            Log.d("test",scd.getEnd_date());
                            //if(scd.get)
                        }
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
                Toast.makeText(getActivity(), "menu_home_add", Toast.LENGTH_SHORT).show();
                if (homeAdapter.getItemCount() >= 6) {
                    showLimitAlterDialog();
                    break;
                }
                break;
            case R.id.menu_home_remove:
                //Toast.makeText(getActivity(), "menu_home_remove", Toast.LENGTH_SHORT).show();
                homeAdapter.removeItem();
                homeAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_home_setting:
                Toast.makeText(getActivity(), "menu_home_setting", Toast.LENGTH_SHORT).show();
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
}
