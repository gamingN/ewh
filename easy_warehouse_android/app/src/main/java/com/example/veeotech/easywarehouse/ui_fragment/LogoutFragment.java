package com.example.veeotech.easywarehouse.ui_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.veeotech.easywarehouse.LoginActivity;
import com.example.veeotech.easywarehouse.R;
import com.example.veeotech.easywarehouse.utils.ActivityUtil;

/**
 * Created by VeeoTech on 3/4/2018.
 */

public class LogoutFragment extends Fragment {
    private Button bt_logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_logout, container, false);
        bt_logout = (Button) view.findViewById(R.id.bt_logout);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                ActivityUtil.getInstance().OutSign();
            }
        });

        return view;
    }
}
