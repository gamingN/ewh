package com.example.veeotech.easywarehouse;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.bean.Zonebean;
import com.example.veeotech.easywarehouse.http.IServer;
import com.example.veeotech.easywarehouse.utils.HttpUtils;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;
import com.example.veeotech.easywarehouse.utils.ScannerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.functions.Action1;

/**
 * 出倉掃碼頁面
 * Created by VeeoTech on 2018/3/22.
 */

public class ChuActivity extends BaseActivity {
    private  Button bt_checktwo_chu;
    private  Button bt_checkone_chu;
    private String pass_result;
    IntentIntegrator integrator;
    private List<Zonebean> zonebeanlists = new ArrayList<>();
    private String zondid;
    private String isurgent;

    private String key_uid;
    private String key_brand;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()!=null){
            key_uid=getIntent().getStringExtra(IntentKeyUtil.KEY_UID);
            key_brand=getIntent().getStringExtra(IntentKeyUtil.KEY_BRAND);
        }
        bt_checktwo_chu=(Button) findViewById(R.id.bt_erwei_chu);
        bt_checkone_chu=(Button) findViewById(R.id.bt_tiaoxing_chu);
        bt_checktwo_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(getApplicationContext())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(ChuActivity.this,integrator).selectTwoActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        bt_checkone_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(getApplicationContext())
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                                    new ScannerUtil(ChuActivity.this,integrator).selectOneActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void init() {
        setToolBarTitle("出倉掃碼");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chu;
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "掃碼取消！", Toast.LENGTH_LONG).show();
            } else {
                pass_result=result.getContents();
                if(key_uid!=null&&key_brand!=null) {
                    request(pass_result, key_uid, key_brand);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        integrator=null;
    }

    private void request(String pass_sku,String key_uid,String key_brand) {
        String url = IntentKeyUtil.REQUEST_URI;
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpUtils.getclient())
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        IServer ipService = retrofit.create(IServer.class);
        Call<String> call = ipService.getUpdateOut(pass_sku,key_uid,key_brand,(String) SPUtils.get(getApplicationContext(),IntentKeyUtil.KEY_AID, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("KIKI", "onResponse: chu"+response.body());
                Gson gson = new Gson();
                List<Zonebean> zonelists = gson.fromJson(response.body(), new TypeToken<List<Zonebean>>() {
                }.getType());
                zonebeanlists.clear();
                if (zonelists != null) {
                    for (Zonebean zone : zonelists) {
                        zonebeanlists.add(zone);
                        if (zonebeanlists.get(0).getZone_id() != null) {
                            zondid=zonebeanlists.get(0).getZone_id();
                        } else {
                            zondid="null";
                        }
                        isurgent=zonebeanlists.get(0).getIs_urgent();
                    }
                }else{
                    zondid="null";
                    isurgent="null";
                }
                Intent intent = new Intent(getApplicationContext(), ExitInfoAcitivity.class);
                intent.putExtra("zondid",zondid);
                intent.putExtra("isurgent",isurgent);
                startActivity(intent);
                finish();
//                Intent broadcast = new Intent("android.intent.action.chuActivity");
//                broadcast.putExtra("zondid",zondid);
//                broadcast.putExtra("isurgent",isurgent);
//                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
