package com.example.veeotech.easywarehouse.ui_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.veeotech.easywarehouse.OrderFragment;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.adapter.OrderAdapter;
import com.example.veeotech.easywarehouse.adapter.MyFragmentPageAdapter;
import com.example.veeotech.easywarehouse.bean.ExitBean;
import com.example.veeotech.easywarehouse.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIKI on 2018/4/1.
 * 346409606@qq.com
 */

public class HandleFragment extends Fragment {
    private TabLayout tab_answer;
    private ViewPager vp_answer;

    //   private int flag_open=0;

    private List<OrderBean> lists = new ArrayList<>();
    private List<ExitBean> exitlists=new ArrayList<>();

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);
    private MyFragmentPageAdapter myAdapter;

    private OrderAdapter orderAdapter;

    private String zoneid;
    private String isurgent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_answer, container, false);

        tab_answer = (TabLayout) view.findViewById(R.id.tab_answer);
        vp_answer = (ViewPager) view.findViewById(R.id.vp_answer);

        vp_answer.setOffscreenPageLimit(3);

        initFragmentList();

        myAdapter = new MyFragmentPageAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitleList);
        vp_answer.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tab_answer.setTabMode(TabLayout.MODE_FIXED);
        tab_answer.setupWithViewPager(vp_answer);

        return view;
    }

    private void initFragmentList() {
        if (mTitleList.size() != 0) {
            return;
        }
        mTitleList.add("入倉處理中");
        mTitleList.add("出倉處理中");
        mFragments.add(new OrderFragment(lists));
        mFragments.add(new MoreoverFragment(exitlists));
    }
}
