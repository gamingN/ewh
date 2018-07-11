package com.example.veeotech.easywarehouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

/**
 * Created by VeeoTech on 13/4/2018.
 */

public class ExitInfoAcitivity extends BaseActivity{

    private BroadcastReceiver bordcastReceiver;
    private LocalBroadcastManager broadcastManager;

    TextView tv_zoneid_info;
    TextView tv_isurgent_info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_zoneid_info=(TextView) findViewById(R.id.tv_exitinfo_zoneid);
        tv_isurgent_info= (TextView) findViewById(R.id.tv_exitinfo_isurgent);

        broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.chuActivity");
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
                if(zoneidflag.equals("null")){
                    zoneidflag="出庫";
                }
                tv_zoneid_info.setText(zoneidflag);
                tv_isurgent_info.setText(isurgentflag);
            }
        };
        broadcastManager.registerReceiver(bordcastReceiver, intentFilter);

    }

    @Override
    protected void init() {
        setToolBarTitle("出庫信息");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exitinfo;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(bordcastReceiver);
    }
}
