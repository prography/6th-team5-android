package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skycastle.MyDatabase.AppDatabase;
import com.example.skycastle.MyDatabase.InfoSave;
import com.example.skycastle.MyDatabase.univ_img;

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
    List<InfoSave> univData = new ArrayList<InfoSave>();
    List<univ_img>univ_n=new ArrayList<univ_img>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_page);

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
                    setRetrofit();
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
                .baseUrl("http://bongkasten.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("onResponse", "2");

        final RemoteService remote = retrofit.create(RemoteService.class);
        Call<List<InfoSave>> call = remote.getUnivData();

        call.enqueue(new Callback<List<InfoSave>>() {

            @Override
            public void onResponse(Call<List<InfoSave>> call, Response<List<InfoSave>> response) {
                String test;

                try{
                    univData = response.body();
                    Log.d("univdata", univData.get(0).getDescription());

                    int size=univData.size();

                    final AppDatabase db=AppDatabase.getAppDatabase(getApplicationContext());

                    Runnable runnable1=new Runnable() {
                        @Override
                        public void run() {
                            db.infoSaveDao().insert(univData);
                        }
                    };
                    thread = new Thread(runnable1);
                    thread.start();

                    Runnable runnable2=new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(200);
                            }catch(InterruptedException e){

                            }
                            univ_n=db.infoSaveDao().findUniv();
                            Log.d("univ_n",univ_n.get(0).getSj());
                            Intent intent=new Intent(getApplicationContext(), SelectUnivPage.class);
                            intent.putExtra("univ_n", (Serializable) univ_n);
                            startActivity(intent);

                        }
                    };
                    thread = new Thread(runnable2);
                    thread.start();

                    try{
                        thread.join();
                    }catch (InterruptedException e)
                    {
                    }


                }catch (Exception e){
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<InfoSave>> call, Throwable t) {

            }
        });
    }
}