package com.output.ipsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.output.ipsi.MyDatabase.univ_img;
import com.output.ipsi.ServerData_full.ServerData_full;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PolicyPage extends AppCompatActivity {
    public Button nextBtn;
    public int TERMS_AGREE_1 = 0;
    AppCompatCheckBox checkbox;
    TextView policyview;
    Thread thread;
    List<ServerData_full> univData = new ArrayList<ServerData_full>();
    List<univ_img>univ_n=new ArrayList<univ_img>();
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_page);
        activity = this;
        nextBtn = (Button)findViewById(R.id.nextBtn);
        checkbox = (AppCompatCheckBox)findViewById(R.id.checkbox);
        policyview = (TextView)findViewById(R.id.Policyview);
        policyview.setMovementMethod(new ScrollingMovementMethod());

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TERMS_AGREE_1 = 1;
                } else {
                    TERMS_AGREE_1 = 0;
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전체 약관 체크여부
                if (TERMS_AGREE_1 == 1) {
                    Intent intent_loading = new Intent(activity, LoadingActivity.class);
                    startActivity(intent_loading);
                    setRetrofit();
                    //finish();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"약관을 체크해주세요",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
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
                    univData = response.body();
                    //Log.d("univdata", univData.get(0).getDescription());

                    int size=univData.size();
                    Log.d("check1", univData.get(0).getName());

                    Intent intent=new Intent(getApplicationContext(), SelectUnivPage.class);
                    intent.putExtra("univ_n", (Serializable) univData);
                    startActivity(intent);
                    finish();
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