package com.wrapp.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.wrapp.demo.fragment.FragmentData;
import com.wrapp.demo.fragment.FragmentFriends;
import com.wrapp.demo.fragment.FragmentLogin;

public class MainActivity extends FacebookActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private FragmentData mFragmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentData = FragmentData.findOrCreateRetainFragment(getSupportFragmentManager());
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (DEBUG) Log.d(TAG, "Session: " + session + ", state: " + state + ", e: " + exception);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Session.getActiveSession().getState().isOpened()) {
            mFragmentData.loadFriends();
            showFragment(FragmentFriends.class.getName());
        } else {
            showFragment(FragmentLogin.class.getName());
        }
    }

    public FragmentData getDataFragment() {
        return mFragmentData;
    }

    private void showFragment(String fragmentName) {
        showFragment(fragmentName, null);
    }

    private void showFragment(String fragmentName, Bundle args) {
        if (DEBUG) Log.d(TAG, "showFragment: " + fragmentName);
        FragmentManager fm = getSupportFragmentManager();

        // Do not add the same fragment
        Fragment currentFragment = fm.findFragmentById(R.id.container);
        String name = currentFragment != null ? currentFragment.getClass().getName() : "";
        if (name.equals(fragmentName)) return;

        // Replace what ever we have with a new fragment
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = Fragment.instantiate(this, fragmentName, args);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    public void logout() {
        Session.getActiveSession().closeAndClearTokenInformation();
        showFragment(FragmentLogin.class.getName());
    }
}
