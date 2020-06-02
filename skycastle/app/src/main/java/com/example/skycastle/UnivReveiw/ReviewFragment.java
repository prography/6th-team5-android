package com.example.skycastle.UnivReveiw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.MainActivity;
import com.example.skycastle.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.review_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        List<ReviewItem> data = new ArrayList<ReviewItem>();
        data.add(new ReviewItem("서울대학교", getActivity().getDrawable(R.drawable.seoul), "https://www.univ100.kr/review/55"));
        data.add(new ReviewItem("연세대학교", getActivity().getDrawable(R.drawable.yonsei), "https://www.univ100.kr/review/1"));
        data.add(new ReviewItem("한양대학교", getActivity().getDrawable(R.drawable.hanyang), "https://www.univ100.kr/review/6"));

        reviewAdapter = new ReviewAdapter(getContext(), data);
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
                ReviewItem item = reviewAdapter.getItem(position);

                ((MainActivity)getActivity()).replaceFragment(new WebviewFragment());

                Toast.makeText(getContext(), "univname : "+item.getUnivName(), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
