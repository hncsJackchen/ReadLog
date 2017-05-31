package com.jack.readlog.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.readlog.NDKUtils;
import com.jack.readlog.R;
import com.jack.readlog.adapter.ResultAdapter;
import com.jack.readlog.model.Result;
import com.jack.readlog.utils.LogPlus;
import com.jack.readlog.utils.SharePreUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mBtnSetting;
    private Button mBtnRead;
    private TextView mTvOutput;

    private List<Result> mResults;
    private ResultAdapter mResultAdapter;
    private ListView mLvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSetting = (Button) findViewById(R.id.btn_main_setting);
        mBtnSetting.setOnClickListener(this);
        mBtnRead = (Button) findViewById(R.id.btn_main_read);
        mBtnRead.setOnClickListener(this);
        mTvOutput = (TextView) findViewById(R.id.tv_main_destDir);
        mResults = new ArrayList<>();
        mResultAdapter = new ResultAdapter(this, mResults);
        mLvResult = (ListView) findViewById(R.id.lv_main_result);
        mLvResult.setEmptyView(findViewById(R.id.tv_main_tip));
        mLvResult.setAdapter(mResultAdapter);
        loadData();
        EventBus.getDefault().register(this);
    }

    private void loadData() {
        mResults.clear();
        Set<String> paths = SharePreUtils.getPaths(this);
        Result result;
        for (String path : paths) {
            result = new Result();
            result.setSrcDir(path);
            mResults.add(result);
        }
        mResultAdapter.notifyDataSetChanged();
        mTvOutput.setText(SharePreUtils.getOutputPath(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_setting:
                SettingActivity.startAct(this);
                break;
            case R.id.btn_main_read:
                getSdcardLog();
                getDataLog();
//                toDir();
                break;
            default:
                break;
        }
    }

    /**
     * 读取sdcard中的数据
     */
    private void getSdcardLog() {
        if (mResults == null || mResults.size() <= 0) {
            Toast.makeText(this, "请配置路径", Toast.LENGTH_SHORT).show();
            return;
        }

        String outputPath = mTvOutput.getText().toString();
        if (outputPath == null || "".equals(outputPath)) {
            Toast.makeText(this, "请配置输出路径", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkMountedState()) {
            Toast.makeText(this, "没有检测到外部存储卡", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            read();
        }
    }


    /**
     * 读取 data中的数据
     */
    private void getDataLog() {
        LogPlus.i("读取 data中的数据");
    }

    /**
     * 检测外部存储器挂载情况
     *
     * @return true-已经挂载，false-没有挂载
     */
    private boolean checkMountedState() {
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * 检测权限
     *
     * @return true-有权限,false-无权限
     */
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * 申请权限回调
     *
     * @param requestCode  请求码
     * @param permissions  申请的权限
     * @param grantResults 申请结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogPlus.i("同意授权");
                    read();
                    return;
                }
                LogPlus.i("不同意授权");
                break;
        }
    }

    private void read() {
        LogPlus.i("读取 sdcard 中的数据");
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory, SharePreUtils.getOutputPath(this));
        if (!file.exists()) {
            file.mkdirs();
        }

        for (Result result : mResults) {
            String srcDir = result.getSrcDir();
            if (srcDir != null && !"".equals(srcDir)) {
                String cmd = "cp -r " + srcDir + " /sdcard/"+mTvOutput.getText().toString();
                LogPlus.w("cmd:"+cmd);
                String execute = NDKUtils.execute(cmd);
                result.setState(Result.State.SUCCESS.getIndex());
            }
        }
        mResultAdapter.notifyDataSetChanged();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("读取完成\n输出路径为："+SharePreUtils.getOutputPath(this));
        builder.show();
    }

    @Subscribe
    public void refreshView(String info){
        LogPlus.i("refreshView");
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 跳转到指定目录
     */
    private void toDir(){
//        File sdDir = Environment.getExternalStorageDirectory();//获取跟目录
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sdDir.getPath()));
//        startActivity(intent);

//        Intent mIntent = new Intent( );
//        ComponentName comp = new ComponentName("com.mediatek.filemanager", "com.mediatek.filemanager.FileManagerOperationActivity");
//        mIntent.setComponent(comp);
//        mIntent.setAction("android.intent.action.VIEW");
//        startActivity(mIntent);


//        final int REQUEST_CODE_SELECT_IMAGE = 1;
//        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "file/*");
//        startActivityForResult(openAlbumIntent, REQUEST_CODE_SELECT_IMAGE);

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.setAction(Intent.AC);
//        intent.setType("file/*");
//        startActivity(intent);
    }

}
