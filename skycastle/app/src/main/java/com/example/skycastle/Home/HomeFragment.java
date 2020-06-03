package com.example.skycastle.Home;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.R;
import com.example.skycastle.SettingFragment;
import com.example.skycastle.MainActivity;

import java.util.ArrayList;
import java.util.List;

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

        List<HomeItem> data = new ArrayList<>();
        data.add(new HomeItem(HomeAdapter.HEADER, "seoul","서울대학교", "지역균형선발전형", "2020-06-23"));
        data.add(new HomeItem(HomeAdapter.CHILD, "seoul","서울대학교", "일반전형", "2020-06-21"));
        data.add(new HomeItem(HomeAdapter.CHILD, "seoul","서울대학교", "기회균형선발특별전형", "2019-12-17"));
        data.add(new HomeItem(HomeAdapter.HEADER, "hanyang","한양대학교", "지역균형선발전형", "2019-09-06"));
        data.add(new HomeItem(HomeAdapter.CHILD, "hanyang","한양대학교", "일반전형", "2019-11-21"));
        data.add(new HomeItem(HomeAdapter.CHILD, "hanyang","한양대학교", "기회균형선발특별전형", "2019-12-17"));
        data.add(new HomeItem(HomeAdapter.HEADER, "yonsei","연세대학교", "지역균형선발전형", "2019-09-06"));
        data.add(new HomeItem(HomeAdapter.CHILD, "yonsei","연세대학교", "일반전형", "2019-11-21"));
        data.add(new HomeItem(HomeAdapter.CHILD, "yonsei","연세대학교", "기회균형선발특별전형", "2019-12-17"));

        homeAdapter = new HomeAdapter(getContext(), data);

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
                if (homeAdapter.getItemCount() >= 6) {
                    showLimitAlterDialog();
                    break;
                }
                break;
            case R.id.menu_home_remove:
                //Toast.makeText(getActivity(), "menu_home_remove", Toast.LENGTH_SHORT).show();
                homeAdapter.removeItem();
                homeAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_home_setting:
                Toast.makeText(getActivity(), "menu_home_setting", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(new SettingFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLimitAlterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("경고창").setMessage("6개 이상 추가할 수 없습니다.");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
