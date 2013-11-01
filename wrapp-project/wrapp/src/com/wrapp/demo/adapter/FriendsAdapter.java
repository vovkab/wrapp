package com.wrapp.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.wrapp.demo.R;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    private ArrayList<GraphUser> mItems = new ArrayList<GraphUser>();

    private Context mContext;

    public FriendsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public GraphUser getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            // Create new layout
            convertView = View.inflate(mContext, R.layout.item_list_friends, null);

            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GraphUser user = getItem(position);
        holder.nameView.setText(user.getName());

        return convertView;
    }

    public void setData(List<GraphUser> friends) {
        mItems.clear();
        mItems.addAll(friends);
        notifyDataSetInvalidated();
    }

    private class ViewHolder {
        TextView nameView;
    }
}
