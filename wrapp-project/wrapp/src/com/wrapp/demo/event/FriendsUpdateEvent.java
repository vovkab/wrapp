package com.wrapp.demo.event;

import de.greenrobot.event.EventBus;

public class FriendsUpdateEvent {
    public static void post() {
        EventBus.getDefault().post(new FriendsUpdateEvent());
    }
}
