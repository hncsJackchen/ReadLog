package com.jack.readlog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jack.readlog.R;
import com.jack.readlog.model.Result;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<Result> mResults;


    public ResultAdapter(Context context, List<Result> results) {
        mContext = context;
        mResults = results;
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Result getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_result, null);
            holder.mTvSrcDir = (TextView) convertView.findViewById(R.id.tv_item_result_srcdir);
            holder.mTvCopyResult = (TextView) convertView.findViewById(R.id.tv_item_result_copyResult);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Result item = getItem(position);
        holder.mTvSrcDir.setText(""+item.getSrcDir());
        Result.State state = Result.State.getState(item.getState());
        if (state != null) {
            holder.mTvCopyResult.setText(state.getInfo());
        }else {
            holder.mTvCopyResult.setText("状态错误");
        }
        return convertView;
    }

    class ViewHolder{
        TextView mTvSrcDir;
        TextView mTvCopyResult;
    }
}
