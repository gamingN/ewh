package com.example.veeotech.easywarehouse;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.adapter.WarehouseAdapter;
import com.example.veeotech.easywarehouse.bean.WarehouseBean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VeeoTech on 2018/5/18.
 */

public class SelectWhActivity extends BaseActivity  {
    private RecyclerView rv_warehouse_select;
    private WarehouseAdapter warehouseAdapter;
    private List<WarehouseBean.DataBean> warehouseBean;
    private int if_zone_flag;
    private String sku;
    private OnItemWhClickListener onItemWhClickListener;
    @Override
    protected void init() {
        setToolBarTitle("請選擇貨品");
        //Item點擊后回調方法結束Activity
        onItemWhClickListener = new OnItemWhClickListener() {
            @Override
            public void OnItemWhClick() {
                finish();
            }
        };

        warehouseBean = new ArrayList<>();
        if(getIntent()!=null) {
            sku = getIntent().getStringExtra("sku");
            if_zone_flag = getIntent().getIntExtra("IntentKeyUtil.KEY_IF_ZONE",1);
        }
        rv_warehouse_select = (RecyclerView) findViewById(R.id.rv_warehouse_select_warehouse);
        warehouseAdapter = new WarehouseAdapter(warehouseBean,if_zone_flag,onItemWhClickListener);
        rv_warehouse_select.setLayoutManager(new LinearLayoutManager(this));
        rv_warehouse_select.setAdapter(warehouseAdapter);
        if(sku!=null) {
            requesEasyWhCount(sku);
        }else {
            Toast.makeText(this, "網絡錯誤,請稍後再試", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_warehouse;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }

    private void requesEasyWhCount(final String sku){
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<WarehouseBean> call = ipService.getEasyWhCount(sku);
        call.enqueue(new Callback<WarehouseBean>() {
            @Override
            public void onResponse(Call<WarehouseBean> call, Response<WarehouseBean> response) {
                   warehouseBean.clear();
               if(response.body().getData()!=null){
                   for(WarehouseBean.DataBean databean:response.body().getData()){
                       warehouseBean.add(databean);
                   }
                   warehouseAdapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onFailure(Call<WarehouseBean> call, Throwable t) {

            }
        });
    }
}
