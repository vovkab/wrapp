package com.wrapp.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

public abstract class FacebookActivity extends ActionBarActivity implements Session.StatusCallback {

    private UiLifecycleHelper mUiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUiHelper = new UiLifecycleHelper(this, this);
        mUiHelper.onCreate(savedInstanceState);
    }

    public UiLifecycleHelper getUiHelper() {
        return mUiHelper;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUiHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUiHelper.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiHelper.onDestroy();
    }

}
