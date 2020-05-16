package com.example.skycastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class SelectUnivPage extends AppCompatActivity {
    RecyclerView recyclerView;
    recyclerAdapter adapter;
    GridLayoutManager layoutManager;

    ArrayList<UnivList> list = new ArrayList<UnivList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_univ_page);

        recyclerView = (RecyclerView)findViewById(R.id.univ_recycler_view);
        adapter = new recyclerAdapter(getApplicationContext(), list);

        layoutManager = new GridLayoutManager(getApplicationContext(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        //임시로 데이터 추가
        String[] n_list = {"서울대학교","연세대학교","고려대학교","서강대학교","성균관대학교","한양대학교","중앙대학교","경희대학교","숙명여자대학교"};
        for(int i=0;i<n_list.length;i++){
            UnivList univ = new UnivList();
            univ.setName(n_list[i]);
            list.add(univ);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
