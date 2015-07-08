package com.polidea.statemachine.sample.events;

import android.support.v4.app.Fragment;

public class BackStackChangedEvent {

    private final Fragment currentFragment;

    public BackStackChangedEvent(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
