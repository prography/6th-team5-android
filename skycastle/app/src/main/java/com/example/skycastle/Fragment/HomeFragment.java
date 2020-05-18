package com.example.skycastle.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.HomeAdapter;
import com.example.skycastle.HomeItem;
import com.example.skycastle.R;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.home_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        homeAdapter = new HomeAdapter(getContext());
        homeAdapter.addItem(new HomeItem("서울대학교", "지역균형선발전형", "2019-09-06"));
        homeAdapter.addItem(new HomeItem("서울대학교", "일반전형", "2019-11-21"));
        homeAdapter.addItem(new HomeItem("서울대학교", "기회균형선발특별전형", "2019-12-17"));
        homeAdapter.addItem(new HomeItem("한양대학교", "지역균형선발전형", "2019-09-06"));
        homeAdapter.addItem(new HomeItem("한양대학교", "일반전형", "2019-11-21"));
        homeAdapter.addItem(new HomeItem("한양대학교", "기회균형선발특별전형", "2019-12-17"));
        homeAdapter.addItem(new HomeItem("연세대학교", "지역균형선발전형", "2019-09-06"));
        homeAdapter.addItem(new HomeItem("연세대학교", "일반전형", "2019-11-21"));
        homeAdapter.addItem(new HomeItem("연세대학교", "기회균형선발특별전형", "2019-12-17"));

        recyclerView.setAdapter(homeAdapter);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fg_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_add:
                Toast.makeText(getActivity(), "menu_home_add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_home_remove:
                Toast.makeText(getActivity(), "menu_home_remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_home_setting:
                Toast.makeText(getActivity(), "menu_home_setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
