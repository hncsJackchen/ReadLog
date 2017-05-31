package com.jack.readlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jack.readlog.R;
import com.jack.readlog.adapter.EditPathAdapter;

/**
 * 编辑解
 */
public class EditActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvTitle;
    private Button mBtnConfirm;
    private EditText mEtContent;
    private int mPosition;
    private String mData;

    public static int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mTvTitle = (TextView) findViewById(R.id.tv_edit_title);
        mBtnConfirm = (Button) findViewById(R.id.btn_edit_confirm);
        mBtnConfirm.setOnClickListener(this);
        mEtContent = (EditText) findViewById(R.id.et_edit_content);

        mPosition = getIntent().getIntExtra("position", 1);
        mData = getIntent().getStringExtra("data");
        if (type == EditPathAdapter.REQUEST_CODE_EDIT) {
            mTvTitle.setText("编辑");
            if (mData != null && !"".equals(mData)) {
                mEtContent.setText(mData);
                mEtContent.setSelection(mData.length());
            }
        }else {
            mTvTitle.setText("添加");
        }

    }

    /**
     * 编辑打开
     * @param context
     * @param data
     * @param requestCode
     */
    public static void startActForEdit(Activity context, int position, String data,int requestCode){
        EditActivity.type = requestCode;
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("data", data);
        context.startActivityForResult(intent,requestCode);
    }

    /**
     * 添加打开
     * @param context
     * @param requestCode
     */
    public static void startActForAdd(Activity context, int requestCode){
        EditActivity.type = requestCode;
        Intent intent = new Intent(context, EditActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_confirm:
                confirm();
                break;
            default:
                break;
        }
    }

    private void confirm() {
        String s = mEtContent.getText().toString();
        Intent intent = new Intent();
        if (type == EditPathAdapter.REQUEST_CODE_EDIT) {
            intent.putExtra("position", mPosition);
        }
        intent.putExtra("data", s);
        setResult(RESULT_OK, intent);
        finish();
    }
}
