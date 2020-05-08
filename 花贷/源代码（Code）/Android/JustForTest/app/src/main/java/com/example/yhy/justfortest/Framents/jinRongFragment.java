package com.example.yhy.justfortest.Framents;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yhy.justfortest.Adapter.ServiceAdapter;
import com.example.yhy.justfortest.R;

import java.util.ArrayList;
import java.util.List;

public class jinRongFragment extends Fragment {
    public jinRongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    RecyclerView recyclerView;
    ServiceAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_jinrong, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        List<String>names=new ArrayList<>();
        names.add("资产继承计划");
        names.add("少儿精英成长计划");
        names.add("老年规划方案");
        names.add("财务安全方案");
        names.add("短期理财方案");
        List<String>price=new ArrayList<>();
        price.add("$1000");
        price.add("$800");
        price.add("$700");
        price.add("$1200");
        price.add("$1100");
        adapter = new ServiceAdapter(getActivity(),names,price );

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
