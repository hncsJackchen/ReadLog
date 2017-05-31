package com.jack.readlog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jack.readlog.Constants;
import com.jack.readlog.R;
import com.jack.readlog.utils.SharePreUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnPath;
    private EditText mEtOutputPath;
    private Button mBtnEdit;

    private String mOldOutputPath;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mBtnPath = (Button) findViewById(R.id.btn_setting_path);
        mBtnPath.setOnClickListener(this);
        mBtnEdit = (Button) findViewById(R.id.btn_setting_modify);
        mBtnEdit.setOnClickListener(this);
        mBtnEdit.setVisibility(View.GONE);
        mEtOutputPath = (EditText) findViewById(R.id.et_setting_output_path);
        mEtOutputPath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str != null && !"".equals(str) && !mOldOutputPath.equals(str)) {
                    mBtnEdit.setVisibility(View.VISIBLE);
                }else {
                    mBtnEdit.setVisibility(View.GONE);
                }
            }
        });
        loadData();
    }

    private void loadData() {
        mOldOutputPath = SharePreUtils.getOutputPath(this);
        if (mOldOutputPath != null && !"".equals(mOldOutputPath)) {
            mEtOutputPath.setText(mOldOutputPath);
            mEtOutputPath.setSelection(mOldOutputPath.length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting_path:
                EditPathActivity.startAct(this);
                break;
            case R.id.btn_setting_modify:
                String s = mEtOutputPath.getText().toString();
                SharePreUtils.setOutputPath(this, s);
                EventBus.getDefault().post(Constants.EB_TAG_REFRESH_MAIN_ACTIVITY);
                mBtnEdit.setVisibility(View.GONE);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
