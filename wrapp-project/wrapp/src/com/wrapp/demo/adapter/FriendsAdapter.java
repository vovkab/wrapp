package com.wrapp.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.webimageloader.ext.ImageHelper;
import com.wrapp.demo.R;
import com.wrapp.demo.WrappApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    private ArrayList<GraphUser> mItems = new ArrayList<GraphUser>();

    private Context mContext;
    private ImageHelper mImageHelper;
    private StringBuilder mStringBuilder = new StringBuilder();

    public FriendsAdapter(Context context) {
        mContext = context;

        // Setup Loader
        mImageHelper = new ImageHelper(mContext, WrappApp.getLoader(mContext))
                .setErrorResource(R.drawable.ic_no_avatar)
                .setLoadingResource(R.drawable.ic_no_avatar)
                .setFadeIn(true);
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

        // TODO: cache url or parse response manually and take image url from there
        mStringBuilder.setLength(0);
        mStringBuilder.append("https://graph.facebook.com/");
        mStringBuilder.append(user.getId());
        mStringBuilder.append("/picture");

        mImageHelper.load(holder.pictureView, mStringBuilder.toString());

        return convertView;
    }

    public void setData(List<GraphUser> friends) {
        // Sort by name
        Collections.sort(friends, mUserComparator);

        mItems.clear();
        mItems.addAll(friends);
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
