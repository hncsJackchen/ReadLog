package com.jack.readlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jack.readlog.Constants;
import com.jack.readlog.R;
import com.jack.readlog.adapter.EditPathAdapter;
import com.jack.readlog.utils.SharePreUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 编辑路径
 */
public class EditPathActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnConfirm;
    private ListView mLvPaths;
    private List<String> mPaths;
    private EditPathAdapter mPathAdapter;

    public static void startAct(Activity context) {
        Intent intent = new Intent(context, EditPathActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_path);
        mBtnConfirm = (Button) findViewById(R.id.btn_edit_confirm);
        mBtnConfirm.setOnClickListener(this);

        mLvPaths = (ListView) findViewById(R.id.lv_edit_path_list);
        mPaths = new ArrayList<>();
        mPathAdapter = new EditPathAdapter(this, mPaths);
        mLvPaths.setAdapter(mPathAdapter);
        loadData();
    }

    private void loadData() {
        Set<String> paths = SharePreUtils.getPaths(this);
        mPaths.clear();
        for (String path : paths) {
            mPaths.add(path);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        Set<String> paths = new HashSet<>();
        if (mPaths != null && mPaths.size() > 0) {
            for (String mPath : mPaths) {
                paths.add(mPath);
            }
        }
        SharePreUtils.setPaths(this, paths);
        EventBus.getDefault().post(Constants.EB_TAG_REFRESH_MAIN_ACTIVITY);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == EditPathAdapter.REQUEST_CODE_EDIT) {
                int position = intent.getIntExtra("position", 0);
                String data = intent.getStringExtra("data");
                if (data == null || "".equals(data)) {
                    mPaths.remove(position);
                }else {
                    mPaths.set(position, data);
                }
            } else if (requestCode == EditPathAdapter.REQUEST_CODE_ADD) {
                String data = intent.getStringExtra("data");
                if (data != null && !"".equals(data)) {
                    mPaths.add(data);
                }
            }
            mPathAdapter.notifyDataSetChanged();
        }
    }
}
