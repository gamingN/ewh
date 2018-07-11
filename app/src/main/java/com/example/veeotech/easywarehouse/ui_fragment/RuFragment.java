package com.example.veeotech.easywarehouse.ui_fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.AnswerActivity;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.SelectWhActivity;
import com.example.veeotech.easywarehouse.bean.WarehouseBean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;

import com.example.veeotech.easywarehouse.utils.SPUtils;
import com.example.veeotech.easywarehouse.utils.ScannerUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions.RxPermissions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action1;

/**
 * Created by KIKI on 2018/4/1.
 * 346409606@qq.com
 */

public class RuFragment extends Fragment {
    private Button bt_checktwo;
    private Button bt_checkone;
//    private String str_result;
    private IntentIntegrator integrator;

    private int if_zone_flag=1;
    //标记Warehouse的数量
    private int flag = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ru, container, false);

        bt_checktwo = (Button) view.findViewById(R.id.bt_erwei);
        bt_checkone = (Button) view.findViewById(R.id.bt_tiaoxing);
        bt_checktwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(getActivity())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(RuFragment.this, integrator).selectTwo();
                                } else {
                                    Toast.makeText(getActivity(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        bt_checkone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(getActivity())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(RuFragment.this, integrator).selectOne();
                                } else {
                                    Toast.makeText(getActivity(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "掃碼取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
//              str_result = result.getContents();

                String sku = result.getContents();
                requestEasyWhCount(sku);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        integrator = null;
    }

    private void requestEasyWhCount(final String sku){
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
                Log.d("zwx",""+response.body().getFlag());
                if(response.body().getFlag()==0){
                    Intent intent = new Intent(getActivity(), AnswerActivity.class);
                    intent.putExtra("sku", sku);
                    intent.putExtra("uid","");
                    intent.putExtra("brand","");
                    intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                    startActivity(intent);
                }else if(response.body().getFlag()==1){
                    Intent intent = new Intent(getActivity(), AnswerActivity.class);
                    intent.putExtra("sku", sku);
                    intent.putExtra("uid",response.body().getData().get(0).getUid());
                    intent.putExtra("brand",response.body().getData().get(0).getBrand());
                    intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                    startActivity(intent);
                }else if(response.body().getFlag()==2){
                    Intent intent = new Intent(getActivity(), SelectWhActivity.class);
                    intent.putExtra("sku", sku);
                    intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<WarehouseBean> call, Throwable t) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
}
