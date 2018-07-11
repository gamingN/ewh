package com.example.veeotech.easywarehouse.ui_fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.AnswerActivity;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.ScannerUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions.RxPermissions;

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

                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                String sku = result.getContents();
                intent.putExtra("sku", sku);
                intent.putExtra(IntentKeyUtil.KEY_IF_ZONE,if_zone_flag);
                startActivity(intent);

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
}
