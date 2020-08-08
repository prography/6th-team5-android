package com.output.ipsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.edge.inappupdate.UpdateListener;
import com.edge.inappupdate.UpdateManager;
import com.edge.inappupdate.UpdateType;
import com.google.android.play.core.appupdate.AppUpdateInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                finish();
            }
        });
        if (isFirstRun==false) {
            UpdateManager.Builder builder = new UpdateManager.Builder()
                    .setActivity(this)
                    .setUpdateType(UpdateType.FLEXIBLE)
                    .setSnackBarMessage("업데이트가 완료 되었습니다")
                    .setSnackbarBtnColor(ContextCompat.getColor(this,R.color.colorAccent));
            final UpdateManager updateManager = builder.create();
            updateManager.setUpdateListener(new UpdateListener() {
                @Override
                public void onUpdateChecked(@NotNull AppUpdateInfo appUpdateInfo, boolean updateAvailable) {
                    if (updateAvailable){
                        updateManager.update(appUpdateInfo);
                    }

                }

                @Override
                public void onUpdateCheckFailure(@Nullable Exception exception) {
                    if (exception!=null){
                        Log.d("error",exception.getMessage());
                        //Toast.makeText(MainActivity.this, "error : " +  exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            updateManager.checkUpdate();
            Intent newIntent = new Intent(getApplicationContext(), BaseActivity.class);
            startActivity(newIntent);
            finish();
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
