package com.example.veeotech.easywarehouse.old;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.AnswerActivity;
import com.example.veeotech.easywarehouse.BaseActivity;
import com.example.veeotech.easywarehouse.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * Created by VeeoTech on 2018/3/22.
 */

public class RuActivity extends BaseActivity {
    Button bt_checktwo;
    Button bt_checkone;
    String str_result;
    IntentIntegrator integrator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt_checktwo=(Button) findViewById(R.id.bt_erwei);
        bt_checkone=(Button) findViewById(R.id.bt_tiaoxing);
        bt_checktwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(RuActivity.this)
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
//                                    new ScannerUtil(RuActivity.this,integrator).selectTwo();
                                } else {
                                    Toast.makeText(getApplicationContext(), "沒有權限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        bt_checkone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(RuActivity.this)
                        .request(Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
//                                    new ScannerUtil(RuActivity.this,integrator).selectOne();
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
        setToolBarTitle("入倉掃碼");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ru;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isShowRightText() {
        return false;
    }

//    private void selectOne() {
//        integrator = new IntentIntegrator(this);
//// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("掃描條形碼");
//        integrator.setCameraId(0);  // 使用默认的相机
//        integrator.setBeepEnabled(false); // 扫到码后播放提示音
//        integrator.setCaptureActivity(MyCaptureActivity.class);
//        integrator.initiateScan();
//    }
//
//    private void selectTwo() {
//        integrator = new IntentIntegrator(this);
//// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//        integrator.setPrompt("掃描二維碼");
//        integrator.setCameraId(0);  // 使用默认的相机
//        integrator.setBeepEnabled(false); // 扫到码后播放提示音
//        integrator.setOrientationLocked(true);
//        integrator.setCaptureActivity(MyCaptureActivity.class);
//        integrator.initiateScan();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "掃碼取消！", Toast.LENGTH_LONG).show();


            } else {
//              Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                str_result=result.getContents();

                Intent intent=new Intent(RuActivity.this,AnswerActivity.class);
//                intent.putExtra(KEY_PASSRESULT,str_result);
                String sku=result.getContents();
                intent.putExtra("sku",sku);
                startActivity(intent);

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
}
