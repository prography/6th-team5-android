package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UnivDetail extends Activity {
    private RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_univ_detail);

        Intent intent = getIntent();
        String image= intent.getExtras().getString("image");
        String name = intent.getExtras().getString("name");
        ArrayList<String> susi_n=intent.getExtras().getStringArrayList("susi_n");
        ArrayList<String> susi_t=intent.getExtras().getStringArrayList("susi_t");
        ArrayList<String> major=intent.getExtras().getStringArrayList("major");

        TextView name_t = findViewById(R.id.univ_name);
        name_t.setText(name);

        recyclerview = findViewById(R.id.detail_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<UnivDetail_Item> data = new ArrayList<>();

        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시/수시"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "전형선택"));
        int susi_size=susi_n.size();
        for(int i=0;i<susi_size;i++){
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, susi_n.get(i)));
        }

        //data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "교과전형"));
        //data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "논술전형"));
        //data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "학생부종합전형"));

        recyclerview.setAdapter(new ExpandableListAdapter(data));
        Button okay;
        okay = findViewById(R.id.ok_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
