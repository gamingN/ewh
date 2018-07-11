package com.example.veeotech.easywarehouse;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.adapter.OrderAdapter;
import com.example.veeotech.easywarehouse.bean.OrderBean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by VeeoTech on 2018/3/27.
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OrderAdapter mOrderAdapter;
    private TextView tv_zone;
    private TextView tv_urgent;

    private String str_isUrgent;

    private Button bt_commit;

    private BroadcastReceiver bordcastReceiver;
    private LocalBroadcastManager broadcastManager;

    /**
     * 提交按鈕是否彈窗
     */
    private int commit_flag;

    private List<OrderBean> lists;

    private LinearLayout ll_fragment_order;

    private volatile int flag_open;

    public OrderFragment(){}

    public OrderFragment(List<OrderBean> lists) {
        this.lists = lists;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        require();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huodan, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_huodan);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srfl_huodan);
        bt_commit= (Button) view.findViewById(R.id.bt_order_commit);

        ll_fragment_order= (LinearLayout) view.findViewById(R.id.ll_fragment_order);

        flag_open= (int) SPUtils.get(getActivity(),IntentKeyUtil.KEY_SP_IF_ZONE,0);
        if(flag_open==0){
            ll_fragment_order.setVisibility(View.GONE);
        }
        Log.e("KIKI", "onCreateView: flagopen"+flag_open);
        tv_zone = (TextView) view.findViewById(R.id.tv_zone);
        tv_urgent = (TextView) view.findViewById(R.id.tv_urgent);

        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit_flag= (int) SPUtils.get(getActivity(),IntentKeyUtil.KEY_COMMIT_FLAG,0);
                if(commit_flag==0){
                    commit_update();
                    Toast.makeText(getActivity(), "已提交", Toast.LENGTH_SHORT).show();

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("警告");
                    builder.setMessage("還存在未完成訂單!是否繼續提交操作?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            commit_update();
                            Toast.makeText(getActivity(), "已提交", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDICAL_BROADCAST");
        bordcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String isurgentflag=intent.getStringExtra("isurgent");
                if(isurgentflag.equals("1")){
                    isurgentflag="急!";
                }else{
                    isurgentflag="";
                }
                String zoneidflag=intent.getStringExtra("zondid");
//                if(zoneidflag.equals("null")){
//                    zoneidflag="入庫(未分貨架)";
//                }
                tv_zone.setText(zoneidflag);
                tv_urgent.setText(isurgentflag);
            }
        };
        broadcastManager.registerReceiver(bordcastReceiver, intentFilter);

//        initData();

        initView();
        return view;
    }

    private void commit_update() {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getCommitUpdate((String) SPUtils.get(getActivity(), IntentKeyUtil.KEY_AID, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                onRefresh();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onRefresh();
            }
        });
    }

    private void initView() {
        mOrderAdapter = new OrderAdapter(lists);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mOrderAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void refresh(List<OrderBean> orderBeanList) {
        this.lists = orderBeanList;
        mOrderAdapter.notifyDataSetChanged();
        IntentKeyUtil.FLAG_COMMIT=0;
    }



    private void initData() {
//       SystemClock.sleep(150);
//        zoneStr = (String) SPUtils.get(getActivity(), IntentKeyUtil.KEY_ZONEID, "null");
//        isUrgent = (String) SPUtils.get(getActivity(), IntentKeyUtil.KEY_ISURGENT,"null");
//        Log.e("KIKI", "zoneStr" + zoneStr);
//        Log.e("KIKI", "isUrgent" + isUrgent);
//        if (isUrgent.equals("0")) {
//            isUrgent = "否";
//        } else if (isUrgent.equals("1")) {
//            isUrgent = "是";
//        } else {
//            isUrgent = " ";
//        }
        if(IntentKeyUtil.ISERGENT.equals("0")){
            str_isUrgent="否";
        }else if(IntentKeyUtil.ISERGENT.equals("1")){
            str_isUrgent="是";
        }else{
            str_isUrgent="null";
        }
        tv_zone.setText("去向:" + IntentKeyUtil.ZONEID);
        tv_urgent.setText("急件:" + str_isUrgent);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentKeyUtil.FLAG_COMMIT=0;
                lists.clear();
                require();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 700);

    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    private void require() {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getorderInfo((String) SPUtils.get(getActivity(), IntentKeyUtil.KEY_AID, ""));
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
                    List<OrderBean> orderlists = gson.fromJson(response.body().toString(), new TypeToken<List<OrderBean>>() {
                    }.getType());
                    for (OrderBean order : orderlists) {
                        lists.add(order);
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
    public void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(bordcastReceiver);
    }
}
