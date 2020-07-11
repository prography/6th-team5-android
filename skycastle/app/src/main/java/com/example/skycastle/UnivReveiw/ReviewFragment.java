package com.example.skycastle.UnivReveiw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.BaseActivity;
import com.example.skycastle.R;
import com.example.skycastle.ServerData.ServerData;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    List<ServerData> univData_r = new ArrayList<ServerData>();

    public ReviewFragment(List<ServerData> univData){
        this.univData_r=univData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.review_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        List<ReviewItem> data = new ArrayList<ReviewItem>();
        for(int i=0;i<univData_r.size();i++){
            data.add(new ReviewItem(univData_r.get(i).getName(), univData_r.get(i).getLogo(), univData_r.get(i).getReview_url()));
        }

        reviewAdapter = new ReviewAdapter(getContext(), data);
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
                ReviewItem item = reviewAdapter.getItem(position);

                ((BaseActivity)getActivity()).replaceFragment(new WebViewFragment(item.getUrl()));
            }
        });

        return rootView;
    }
}
