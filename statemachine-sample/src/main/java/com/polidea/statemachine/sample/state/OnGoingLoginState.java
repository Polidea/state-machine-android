package com.polidea.statemachine.sample.state;

import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.events.BackStackChangedEvent;
import com.polidea.statemachine.sample.events.LoginEvent;
import com.polidea.statemachine.sample.manager.NetworkManager;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class OnGoingLoginState extends BaseLoginState {

    @Inject
    NetworkManager networkManager;

    @Override
    protected void injectDependencies() {
        Application.getComponentInstance().inject(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        networkManager.loginUser(loginEvent.getEmail(), loginEvent.getPassword());
        fireEvent(LoginEvents.LOGIN_IN_PROGRESS);
    }

    @Subscribe
    public void onBackStackChangedEvent(BackStackChangedEvent backStackChangedEvent) {
        fireEvent(LoginEvents.CANCELLED);
    }
}
