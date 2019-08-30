package com.mgcoco.errorrecord;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorRecordAdapter extends RecyclerView.Adapter<ErrorRecordAdapter.ViewHolder> {

    private List<ErrorLog> mData;
    private View.OnClickListener mOnItemClickListener;

    private Context mContext;

    public ErrorRecordAdapter(Context context){
        mContext = context;
        mData = new Gson().fromJson(PrefUtil.getStringPreference(mContext, "ERROR_RECORD"), new TypeToken<List<ErrorLog>>(){}.getType());
        if(mData == null)
            mData = new ArrayList<>();
        Collections.reverse(mData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time;
        public ViewHolder(View v) {
            super(v);
            time = v.findViewById(R.id.date);
            title = v.findViewById(R.id.title);
        }
    }

    public void setOnItemClickListener(View.OnClickListener listener){
        mOnItemClickListener = listener;
    }

    @Override
    public ErrorRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_date, parent, false);
        ErrorRecordAdapter.ViewHolder vh = new ErrorRecordAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ErrorRecordAdapter.ViewHolder holder, final int position) {

        holder.time.setText(mData.get(position).getTime());
        holder.title.setText(mData.get(position).getMessage());

        holder.itemView.setTag(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}