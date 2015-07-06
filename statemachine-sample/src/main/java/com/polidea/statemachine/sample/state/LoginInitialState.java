package com.polidea.statemachine.sample.state;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.polidea.statemachine.State;
import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.events.StartLoginEvent;
import com.polidea.statemachine.sample.fragment.LoginFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class LoginInitialState extends State<LoginProvider, LoginActionInterface> {

    @Inject
    Bus bus;

    public LoginInitialState() {
        Application.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        bus.register(this);
    }

    @Override
    public void onStateLeft() {
        bus.unregister(this);
    }

    @Subscribe
    public void onStartLoginEvent(StartLoginEvent startLoginEvent) {
        FragmentManager fragmentManager = getProvider().provideFragmentManager();
        int fragmentContainerId = getProvider().provideFragmentContainerId();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainerId, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        getEventHandler().handleStateEvent(LoginEvents.START_LOGIN, this.getClass());
    }
}
