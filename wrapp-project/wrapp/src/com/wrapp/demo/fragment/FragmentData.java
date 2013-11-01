package com.wrapp.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.wrapp.demo.event.FriendsUpdateEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentData extends FragmentBase {

    public static final String NAME = "fragment_data_retain";

    private ArrayList<GraphUser> mFriendsList = new ArrayList<GraphUser>();
    private boolean mIsFriendsLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static FragmentData findOrCreateRetainFragment(FragmentManager fm) {
        // Check to see if we have retained the worker fragment.
        FragmentData fragmentPageRetain = (FragmentData) fm.findFragmentByTag(NAME);

        // If not retained (or first time running), we need to create and add it.
        if (fragmentPageRetain == null) {
            fragmentPageRetain = new FragmentData();
            fm.beginTransaction().add(fragmentPageRetain, NAME).commitAllowingStateLoss();
        }

        return fragmentPageRetain;
    }

    public ArrayList<GraphUser> getFriendsList() {
        return mFriendsList;
    }

    public boolean isFriendsLoading() {
        return mIsFriendsLoading;
    }

    public void loadFriends() {
        if (mIsFriendsLoading) return;

        mIsFriendsLoading = true;
        Session session = Session.getActiveSession();
        if (session.getState().isOpened()) {
            requestFriends(session);
        }
    }

    private Request createRequest(Session session) {
        Request request = Request.newGraphPathRequest(session, "me/friends", null);

        Set<String> fields = new HashSet<String>();
        String[] requiredFields = new String[]{"id", "name", "picture", "installed"};
        fields.addAll(Arrays.asList(requiredFields));

        Bundle parameters = request.getParameters();
        parameters.putString("fields", TextUtils.join(",", fields));
        request.setParameters(parameters);

        return request;
    }

    private void requestFriends(Session session) {
        Request friendsRequest = createRequest(session);
        friendsRequest.setCallback(new Request.Callback() {

            @Override
            public void onCompleted(Response response) {
                List<GraphUser> users = getResults(response);

                if (users != null) {
                    mFriendsList.clear();
                    mFriendsList.addAll(users);

                    // Notify about new Friends
                    FriendsUpdateEvent.post();
                }

                mIsFriendsLoading = false;
            }
        });

        friendsRequest.executeAsync();
    }

    private List<GraphUser> getResults(Response response) {
        GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
        GraphObjectList<GraphObject> data = multiResult.getData();
        return data.castToListOf(GraphUser.class);
    }

}
