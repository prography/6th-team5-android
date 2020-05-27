package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    boolean isFirstRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE); // 생성하기
        Button b = (Button)findViewById(R.id.startbutton);
        isFirstRun = prefs.getBoolean("isFirstRun", true);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(), PolicyPage.class);
                startActivity(intent);*/
                checkFirstRun();
            }
        });
        if (isFirstRun==false) {
            Intent newIntent = new Intent(getApplicationContext(), SelectUnivPage.class);
            startActivity(newIntent);
        }
    }
    public void checkFirstRun() {

        if (isFirstRun) {
            Intent newIntent = new Intent(getApplicationContext(), PolicyPage.class);
            startActivity(newIntent);

            prefs.edit().putBoolean("isFirstRun", false).apply();
        }

    }

}