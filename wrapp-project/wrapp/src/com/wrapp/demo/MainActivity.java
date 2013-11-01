package com.wrapp.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.wrapp.demo.fragment.FragmentLogin;

public class MainActivity extends FacebookActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = Fragment.instantiate(this, FragmentLogin.class.getName());
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        if (DEBUG) Log.d(TAG, "Session: " + session + ", state: " + state + ", e: " + exception);
    }

}
