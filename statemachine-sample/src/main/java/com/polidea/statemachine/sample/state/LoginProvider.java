package com.polidea.statemachine.sample.state;

import android.support.v4.app.FragmentManager;
import com.polidea.statemachine.StateProvider;

public interface LoginProvider extends StateProvider {

    FragmentManager provideFragmentManager();

    int provideFragmentContainerId();
}
