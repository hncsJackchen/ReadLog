package com.jack.readlog.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jack.readlog.R;
import com.jack.readlog.activity.EditActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */
public class EditPathAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mPaths;
    private LayoutInflater mInflater;

    public static final int REQUEST_CODE_EDIT = 1;
    public static final int REQUEST_CODE_ADD = 2;

    public EditPathAdapter(Context mContext, List<String> mPaths) {
        this.mContext = mContext;
        this.mPaths = mPaths;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mPaths.size();
    }

    @Override
    public String getItem(int position) {
        return mPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_edit_path, null);
            holder.mLvContainer = (LinearLayout) convertView.findViewById(R.id.ll_item_edit_container);
            holder.mTvPath = (TextView) convertView.findViewById(R.id.tv_item_edit_path_src);
            holder.mTvEdit = (TextView) convertView.findViewById(R.id.tv_item_edit_path_edit);
            holder.mRlContainer = (RelativeLayout) convertView.findViewById(R.id.rl_item_edit_container);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String item = getItem(position);
        holder.mLvContainer.setVisibility(View.VISIBLE);
        if (position == mPaths.size() - 1) {
            holder.mRlContainer.setVisibility(View.VISIBLE);
            holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditActivity.startActForAdd((Activity) mContext, REQUEST_CODE_ADD);
                }
            });
        }else {
            holder.mRlContainer.setVisibility(View.GONE);
        }
        holder.mTvPath.setText(item);
        holder.mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.startActForEdit((Activity) mContext,position,item, REQUEST_CODE_EDIT);
            }
        });
        return convertView;
    }

    class ViewHolder{
        LinearLayout mLvContainer;
        TextView mTvPath;
        TextView mTvEdit;
        RelativeLayout mRlContainer;
    }
}
