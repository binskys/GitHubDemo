package com.github.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.app.R;
import com.github.app.ui.WebActivity;
import com.github.app.utils.DataBean;
import com.github.app.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class GitHubAdapter extends BaseAdapter {

    private Context context;
    private List<DataBean> list = new ArrayList<>();

    public GitHubAdapter(Context context, List<DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        GitHubHolder hubHolder=null;
        if (convertView == null) {
            hubHolder=new GitHubHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            hubHolder.title=(TextView)convertView.findViewById(R.id.tv_title);
            hubHolder.describe=(TextView)convertView.findViewById(R.id.tv_describe);
            hubHolder.address=(TextView)convertView.findViewById(R.id.tv_address);
            convertView.setTag(hubHolder);
        } else {
            hubHolder=(GitHubHolder)convertView.getTag();
        }
        hubHolder.title.setText(list.get(position).getTitle());
        hubHolder.describe.setText(list.get(position).getDescribe());
        hubHolder.address.setText(list.get(position).getAddress());
        hubHolder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("beanData",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class GitHubHolder {
        TextView title;
        TextView describe;
        TextView address;
    }
}
