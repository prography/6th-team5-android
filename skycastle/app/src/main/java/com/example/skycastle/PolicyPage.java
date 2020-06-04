package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class PolicyPage extends AppCompatActivity {
    public Button nextBtn;
    public int TERMS_AGREE_1 = 0;
    AppCompatCheckBox checkbox;
    TextView policyview;

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
                    startActivity(new Intent(getApplicationContext(), SelectUnivPage.class));
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"약관을 체크해주세요",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}