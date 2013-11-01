package com.wrapp.demo.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.wrapp.demo.MainActivity;

public abstract class FragmentBase extends Fragment {

    private MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;

        if (getTitleResId() > 0) mMainActivity.setTitle(getTitleResId());
    }

    public MainActivity getMainActivity() {
        return mMainActivity;
    }

    public int getTitleResId() {
        return -1;
    }

}
