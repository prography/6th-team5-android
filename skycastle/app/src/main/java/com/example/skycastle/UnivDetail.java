package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UnivDetail extends Activity {
    private RecyclerView recyclerview;
    static Univ_ServerSend senddata=new Univ_ServerSend();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_univ_detail);

        Intent intent = getIntent();
        String image= intent.getExtras().getString("image");
        String name = intent.getExtras().getString("name");
        ArrayList<String> susi_n=intent.getExtras().getStringArrayList("susi_n");
        ArrayList<String> susi_mb=intent.getExtras().getStringArrayList("susi_mb");
        ArrayList<String> jeongsi_mb=intent.getExtras().getStringArrayList("jeongsi_mb");

        TextView name_t = findViewById(R.id.univ_name);
        name_t.setText(name);

        recyclerview = findViewById(R.id.detail_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<UnivDetail_Item> data = new ArrayList<>();
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시/수시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "수시전형선택","susi_j"));
        int susi_size=susi_n.size();
        for(int i=0;i<susi_size;i++){
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, susi_n.get(i),"susi_j"));
        }
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "수시학과","s_block"));
        int susi_ms=susi_mb.size();
        for(int j=0;j<susi_ms;j++){
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, susi_mb.get(j),"s_block"));
        }
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시 군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "가군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "나군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "다군","gun"));

        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시학과","j_block"));
        int js_ms=jeongsi_mb.size();
        for(int k=0;k<js_ms;k++){
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, jeongsi_mb.get(k),"j_block"));
        }

        recyclerview.setAdapter(new ExpandableListAdapter(data,name));
        Button okay;
        okay = findViewById(R.id.ok_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectUnivPage.univ_serverSends.add(senddata);
                Log.d("check",SelectUnivPage.univ_serverSends.get(0).getSusi_j());

                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
