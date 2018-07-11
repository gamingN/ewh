package com.example.veeotech.easywarehouse;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.veeotech.easywarehouse.utils.ActivityUtil;
import com.example.veeotech.easywarehouse.utils.IntentKeyUtil;
import com.example.veeotech.easywarehouse.utils.SPUtils;

/**
 * Created by VeeoTech on 2018/3/23.
 */

/**
 * Created by VeeoTech on 2018/3/23.
 * 基类,封装toolbar
 */

public abstract class BaseActivity extends AppCompatActivity {
    //the container of this activity layout and sub-activity layout
    private LinearLayout parentLinearLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    private Toolbar mToolbar;
    public static final String KEY_PASSRESULT = "PASSRESULT";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils.put(getApplicationContext(), IntentKeyUtil.KEY_SP_IF_ZONE,0);
        initContentView(R.layout.activity_base);
        ActivityUtil.getInstance().addActivity(this);
        setContentView(getLayoutId());
        initToolBar();
        setBackIcon();
        setRightText();
        init();
    }

    public void setRightText() {
        if (isShowRightText()) {
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightBtnClick();
                }
            });
        }
    }

    protected void rightBtnClick() {

    }

    private void initToolBar() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected abstract void init();


    //  overwrite the function in sub-activity and return the layout id of sub-activity
    protected abstract int getLayoutId();

    private void initContentView(@LayoutRes int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        //  add the layout of BaseActivity in parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * @param layoutResID the layout id of sub Activity
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //  added the sub-activity layout id in parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    private void setBackIcon() {
        if (null != getToolbar() && isShowBacking()) {
            getToolbar().setNavigationIcon(R.drawable.ic_action_backpress);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * @return TextView in center
     */
    public TextView getToolbarTitle() {
        return mTvTitle;
    }

    /**
     * @return TextView on the right
     */
//    public TextView getSubTitle() {
//        return mTvRight;
//    }

    /**
     * set Title
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    public void setRightTvTitle(CharSequence title) {
        mTvRight.setText(title);
    }

    public Toolbar getMyToolbar() {
        return mToolbar;
    }

    /**
     * the toolbar of this Activity
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * is show back icon,default is none。
     * you can override the function in subclass and return to true show the back icon
     *
     * @return
     */
    protected abstract boolean isShowBacking();

    protected abstract boolean isShowRightText();


}
