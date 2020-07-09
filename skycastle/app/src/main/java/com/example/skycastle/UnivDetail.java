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
import android.widget.Toast;

import com.example.skycastle.MyDatabase.BlockData;
import com.example.skycastle.MyDatabase.JhData;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.ServerData.jhs;
import com.example.skycastle.ServerData.sjs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class UnivDetail extends Activity {
    private RecyclerView recyclerview;
    Univ_ServerSend senddata=new Univ_ServerSend();
    //List<UnivDetail_Item> data = new ArrayList<>();
    ServerData detailData;
    String sj="";
    String jh="";
    String major="";
    List<jhs> jhsList;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(DataEvent event){
        List<UnivDetail_Item> data = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if(!event.getSj().equals("")) sj=event.getSj();
        if(!event.getJh().equals("")) jh=event.getJh();
        if(!event.getMajor().equals("")) major=event.getMajor();

        if(!sj.equals("")&&jh.equals("")&&major.equals("")){
            data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, sj,"sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));
            if(sj.equals("수시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "전형선택","jh"));
                for(int i=0;i<detailData.getSjs().get(0).getJhs().size();i++){
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(0).getJhs().get(i).getName(),"jh"));
                }
            }else if(sj.equals("정시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "군 선택","jh"));
                for(int i=0;i<detailData.getSjs().get(1).getJhs().size();i++){
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(1).getJhs().get(i).getName(),"jh"));
                }
            }
        }else if(!sj.equals("")&&!jh.equals("")&&major.equals("")){
            data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, sj,"sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));
            if(sj.equals("수시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, jh,"jh"));
                for(int i=0;i<detailData.getSjs().get(0).getJhs().size();i++){
                    jhsList=detailData.getSjs().get(0).getJhs();
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(0).getJhs().get(i).getName(),"jh"));
                }
            }else if(sj.equals("정시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, jh,"jh"));
                for(int i=0;i<detailData.getSjs().get(1).getJhs().size();i++){
                    jhsList=detailData.getSjs().get(1).getJhs();
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(1).getJhs().get(i).getName(),"jh"));
                }
            }
            data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "학과 선택","major"));
            for(int j=0;j<jhsList.size();j++){
                if(jhsList.get(j).getName().equals(jh)){
                    for(int k=0;k<jhsList.get(j).getMajors().size();k++){
                        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, jhsList.get(j).getMajors().get(k).getName(),"major"));
                    }
                }
            }
        }else if(!sj.equals("")&&!jh.equals("")&&!major.equals("")){
            data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, sj,"sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
            data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));
            if(sj.equals("수시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, jh,"jh"));
                for(int i=0;i<detailData.getSjs().get(0).getJhs().size();i++){
                    jhsList=detailData.getSjs().get(0).getJhs();
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(0).getJhs().get(i).getName(),"jh"));
                }
            }else if(sj.equals("정시")){
                data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, jh,"jh"));
                for(int i=0;i<detailData.getSjs().get(1).getJhs().size();i++){
                    jhsList=detailData.getSjs().get(1).getJhs();
                    data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, detailData.getSjs().get(1).getJhs().get(i).getName(),"jh"));
                }
            }
            data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, major,"major"));
            for(int j=0;j<jhsList.size();j++){
                if(jhsList.get(j).getName().equals(jh)){
                    for(int k=0;k<jhsList.get(j).getMajors().size();k++){
                        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, jhsList.get(j).getMajors().get(k).getName(),"major"));
                    }
                }
            }
        }
        recyclerview.setAdapter(new ExpandableListAdapter(data,detailData.getName()));

        TextView name_t = findViewById(R.id.univ_name);
        String select_t=sj+" "+jh+" "+major+" ";
        name_t.setText(select_t);
        recyclerview = findViewById(R.id.detail_recyclerview);
        senddata.setSj(sj);
        senddata.setJh(jh);
        senddata.setMajor(major);
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
        detailData = (ServerData) intent2.getSerializableExtra("univ_data");
        Log.d("intent",detailData.getSjs().get(0).getSj());
        senddata.setUniv_n(detailData.getName());
        TextView name_t = findViewById(R.id.univ_name);
        name_t.setText(detailData.getName());

        List<UnivDetail_Item> data = new ArrayList<>();
        recyclerview = findViewById(R.id.detail_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        data.add(new UnivDetail_Item(ExpandableListAdapter.HEADER, "정시/수시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "정시","sj"));
        data.add(new UnivDetail_Item(ExpandableListAdapter.CHILD, "수시","sj"));

        recyclerview.setAdapter(new ExpandableListAdapter(data,detailData.getName()));
        Button okay;
        okay = findViewById(R.id.ok_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(senddata.getSj()==null||senddata.getJh()==null||senddata.getMajor()==null){
                    Toast myToast = Toast.makeText(getApplicationContext(),"전형,학과를 모두 골라주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    String selected_t=senddata.getUniv_n()+" "+senddata.getSj()+" "+senddata.getJh()+" "+senddata.getMajor();
                    DataEvent_selected dataEvent2=new DataEvent_selected(selected_t);
                    EventBus.getDefault().post(dataEvent2);

                    SelectUnivPage.univ_serverSends.add(senddata);
                    Log.d("check",SelectUnivPage.univ_serverSends.get(0).getSj());
                    finish();
                }

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
