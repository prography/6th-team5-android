package com.output.ipsi.Offline;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.R;
import com.output.ipsi.RemoteService;
import com.output.ipsi.ServerData.ServerData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfflineFragment extends Fragment {
    private RecyclerView recyclerView;
    List<ServerData> offunivData = new ArrayList<ServerData>();
    List<ServerData> offData = new ArrayList<ServerData>();

    public OfflineFragment(List<ServerData> univData) {
        this.offunivData = univData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_offline, container, false);
        setHasOptionsMenu(true);
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
                    Log.d("checkoff", offData.get(0).getName());
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
}