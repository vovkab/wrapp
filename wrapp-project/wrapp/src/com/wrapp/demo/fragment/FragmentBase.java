package com.wrapp.demo.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.wrapp.demo.MainActivity;

public class FragmentBase extends Fragment {

    private MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    public MainActivity getMainActivity() {
        return mMainActivity;
    }
}
