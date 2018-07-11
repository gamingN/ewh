package com.example.veeotech.easywarehouse.ui_fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veeotech.easywarehouse.BaseActivity;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.bean.HurryBean;
import com.example.veeotech.easywarehouse.bean.OutBean;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIKI on 2018/4/1.
 * 346409606@qq.com
 */

public class TabMainActivity extends BaseActivity {
    private String str_personId;

    private long firstTime = 0;

    private String[] titles = new String[]{"入倉", "出倉", "處理中", "急件", "登出"};
    private TabLayout mTabLayout;
    //    private ViewPager mViewPager;
    private ViewPager mViewPager;

    private TabFragmentAdapter adapter;
    //ViewPage选项卡页面集合
    private List<Fragment> mFragments;
    //Tab标题集合
    private List<String> mTitles;
    /**
     * 图片数组
     */
    private int[] mImgs = new int[]{R.drawable.ic_action_ru, R.drawable.ic_action_chu, R.drawable.ic_action_file, R.drawable.ic_action_hurry, R.drawable.ic_action_me};

    private List<OutBean> outlists=new ArrayList<>();
    private List<HurryBean> hurrylists=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = (ViewPager) findViewById(R.id.tab_mainviewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_mainlayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mTitles.add(titles[i]);
        }

        mFragments = new ArrayList<>();

        mFragments.add(new RuFragment());
//        mFragments.add(new ChuFragment());
        mFragments.add(new OutFragment(outlists));
        mFragments.add(new HandleFragment());
        mFragments.add(new HurryFragment(hurrylists));
        mFragments.add(new LogoutFragment());

        mViewPager.setOffscreenPageLimit(5);

        adapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来

        mTabLayout.setSelectedTabIndicatorHeight(0);

        for (int i = 0; i < mTitles.size(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
            if (itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView(R.layout.item_tab);
                TextView textView = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
                textView.setText(mTitles.get(i));
                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);
                imageView.setImageResource(mImgs[i]);
            }
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @Override
    protected void init() {
        str_personId = (String) SPUtils.get(getApplicationContext(), IntentKeyUtil.KEY_AID, "");
//        str_personId=getIntent().getStringExtra("person_Id");
        setToolBarTitle("用戶:" + str_personId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tab_mainactivity;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
