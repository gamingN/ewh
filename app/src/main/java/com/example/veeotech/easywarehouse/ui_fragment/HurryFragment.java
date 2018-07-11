package com.example.veeotech.easywarehouse.ui_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.adapter.HurryAdapter;
import com.example.veeotech.easywarehouse.bean.HurryBean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by VeeoTech on 3/4/2018.
 */
@SuppressLint("ValidFragment")
public class HurryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<HurryBean> lists;

    private HurryAdapter mhurryAdapter;

    public HurryFragment(){}

    public HurryFragment(List<HurryBean> lists){
        this.lists=lists;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        require();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hurry, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_hurry);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srfl_hurry);

        initData();
        initView();

        return view;
    }

    public void refresh(List<HurryBean> outBeanList) {
        this.lists = outBeanList;
        mhurryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lists.clear();
                require();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 700);
    }

    private void initView() {
        mhurryAdapter = new HurryAdapter(lists);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mhurryAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initData() {
    }

    private void require() {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getHurryInfo();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(getApplicationContext(),getIntent().getStringExtra("sku"), Toast.LENGTH_SHORT).show();
                if (response.body().equals("-1")) {
                    lists.clear();
                    refresh(lists);
//                    Toast.makeText(getActivity(), "沒有數據", Toast.LENGTH_SHORT).show();
                } else {
                    lists.clear();
                    Gson gson = new Gson();
                    List<HurryBean> hurrylists = gson.fromJson(response.body().toString(), new TypeToken<List<HurryBean>>() {
                    }.getType());
                    for (HurryBean hurry : hurrylists) {
                        lists.add(hurry);
                    }
                    refresh(lists);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }
}
