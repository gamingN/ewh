package com.example.veeotech.easywarehouse.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.veeotech.easywarehouse.MyCaptureActivity;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by VeeoTech on 2018/3/27.
 */

public class ScannerUtil {

    IntentIntegrator integrator;
    Activity mActivity;
    public ScannerUtil(Activity activity,IntentIntegrator integrator){
        this.mActivity=activity;
        this.integrator=integrator;
    }

    Fragment mFragment;

    public ScannerUtil(Fragment fragment, IntentIntegrator integrator) {
        this.mFragment = fragment;
        this.integrator = integrator;
    }

    public void selectOne() {
        integrator = IntentIntegrator.forSupportFragment(mFragment);
// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("掃描條形碼");
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.initiateScan();
    }

    public void selectTwo() {
        integrator = IntentIntegrator.forSupportFragment(mFragment);
// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("掃描二維碼");
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.initiateScan();
    }

    public void selectOneActivity(){
        integrator = new IntentIntegrator(mActivity);
// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("掃描條形碼");
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.initiateScan();
    }

    public void selectTwoActivity(){
        integrator = new IntentIntegrator(mActivity);
// 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("掃描二維碼");
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.initiateScan();
    }

}
