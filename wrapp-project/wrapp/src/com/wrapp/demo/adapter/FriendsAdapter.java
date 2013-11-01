package com.wrapp.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.wrapp.demo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            holder.pictureView = (ImageView) convertView.findViewById(R.id.picture);
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

        // Sort by name
        Collections.sort(friends, mUserComparator);

        notifyDataSetInvalidated();
    }

    private Comparator<GraphUser> mUserComparator = new Comparator<GraphUser>() {
        @Override
        public int compare(GraphUser lhs, GraphUser rhs) {
            if (lhs == null) return -1;
            if (rhs == null) return 1;
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    private class ViewHolder {
        TextView nameView;
        ImageView pictureView;
    }
}
