package com.wrapp.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wrapp.demo.R;
import com.wrapp.demo.adapter.FriendsAdapter;
import com.wrapp.demo.event.FriendsUpdateEvent;

import de.greenrobot.event.EventBus;

public class FragmentFriends extends FragmentBase {

    private View mFriendsListContainer;
    private View mProgressLoading;

    private ListView mListView;
    private FriendsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFriendsListContainer = view.findViewById(R.id.friends_list_container);
        mProgressLoading = view.findViewById(R.id.progress_loading);

        mAdapter = new FriendsAdapter(getActivity());
        mListView = (ListView) view.findViewById(R.id.friends_list);
        mListView.setEmptyView(view.findViewById(R.id.empty_view));
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getMainActivity().getDataFragment().isFriendsLoading()) {
            showLoading(true);
        } else {
            showLoading(false);
            mAdapter.setData(getMainActivity().getDataFragment().getFriendsList());
        }

        EventBus.getDefault().register(this, FriendsUpdateEvent.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(FriendsUpdateEvent update) {

        mAdapter.setData(getMainActivity().getDataFragment().getFriendsList());
        showLoading(false);
    }

    private void showLoading(boolean isShowLoading) {
        if (isShowLoading) {
            mProgressLoading.setVisibility(View.VISIBLE);
            mFriendsListContainer.setVisibility(View.INVISIBLE);
        } else {
            mProgressLoading.setVisibility(View.GONE);
            mFriendsListContainer.setVisibility(View.VISIBLE);
        }
    }

}
