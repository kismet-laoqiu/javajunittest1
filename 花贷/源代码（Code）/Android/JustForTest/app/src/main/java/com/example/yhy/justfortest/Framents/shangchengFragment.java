package com.example.yhy.justfortest.Framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Activities.GoodInfoActivity;
import com.example.yhy.justfortest.Adapter.GoodAdapter;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.Good;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class shangchengFragment extends Fragment {
    public shangchengFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_shangcheng, container, false);
        final RecyclerView recyclerView=v.findViewById(R.id.shangcheng_good);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        InternetAPI.getApi().shangcheng_page()
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.e("tag","??");
                            Good.goods=response.body().string();
                            final GoodAdapter adapter = new GoodAdapter(getActivity(),Good.goods );
                            adapter.setOnitemClickLintener(new GoodAdapter.OnitemClick() {
                                @Override
                                public void onItemClick(int position) {
                                    try {
                                        JSONArray obj=new JSONArray(Good.goods);
                                        Good.one_good=obj.getJSONObject(position).toString();
                                        Intent intent=new Intent(getActivity(), GoodInfoActivity.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);
                        } catch (IOException e) {
                            Log.e("tag",e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("tag","失败");
                    }

                });

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
