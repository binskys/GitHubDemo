package com.github.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.app.bean.BaseBean;
import com.github.app.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny
 * on 2017/10/18.
 */

public class GitHubBaseAdapter<T> extends BaseAdapter{

    protected Context context;
    protected List<T> list = new ArrayList<>();

    public GitHubBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return itemView(position,convertView,parent);
    }

    protected View itemView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    class GitHubHolder {
        TextView title;
        TextView describe;
        TextView address;
        Button btn_open;
    }
}
