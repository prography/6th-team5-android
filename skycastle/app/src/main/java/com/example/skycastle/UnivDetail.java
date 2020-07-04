package com.example.skycastle;

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

import com.example.skycastle.MyDatabase.BlockData;
import com.example.skycastle.MyDatabase.JhData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class UnivDetail extends Activity {
    private RecyclerView recyclerview;
    static Univ_ServerSend senddata=new Univ_ServerSend();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(DataEvent event){
        TextView name_t = findViewById(R.id.univ_name);
        name_t.setText(event.eventText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_univ_detail);

        try{
            EventBus.getDefault().register(this);
        }catch(Exception e){}

        Intent intent2 = getIntent();
        List<JhData> detailData = (List<JhData>) intent2.getSerializableExtra("jhData");
        List<BlockData> blockData = (List<BlockData>) intent2.getSerializableExtra("blockData");
        Log.d("intent",detailData.get(0).getSj());

        TextView name_t = findViewById(R.id.univ_name);
        name_t.setText(detailData.get(0).getUniversity());

        recyclerview = findViewById(R.id.detail_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<UnivDetail_Item> data = new ArrayList<>();
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시/수시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "수시전형선택","susi_j"));
        int detail_size=detailData.size();
        for(int i=0;i<detail_size;i++){
            if(detailData.get(i).getSj().equals("수시")){
                Log.d("???","dddd");
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.get(i).getJh(),"susi_j"));
            }
        }
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "수시학과","s_block"));

        int block_s=blockData.size();
        for(int j=0;j<block_s;j++){
            if(blockData.get(j).getSj().equals("수시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, blockData.get(j).getBlock(),"s_block"));
            }
        }
        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시 군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "가군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "나군","gun"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "다군","gun"));

        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시학과","j_block"));

        for(int k=0;k<block_s;k++){
            if(blockData.get(k).getSj().equals("정시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, blockData.get(k).getBlock(),"j_block"));
            }
        }

        recyclerview.setAdapter(new ExpandableListAdapter(data,detailData.get(0).getUniversity()));
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            EventBus.getDefault().unregister(this);
        }catch (Exception e){}
    }
}
