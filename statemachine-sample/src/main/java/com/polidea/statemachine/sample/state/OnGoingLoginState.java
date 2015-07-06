package com.polidea.statemachine.sample.state;

import com.polidea.statemachine.State;
import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.NetworkManager;
import com.polidea.statemachine.sample.events.LoginEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class OnGoingLoginState extends State<LoginProvider, LoginActionInterface>{

    @Inject
    Bus bus;

    @Inject
    NetworkManager networkManager;

    public OnGoingLoginState() {
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
    public void onLoginEvent(LoginEvent loginEvent) {
        networkManager.loginUser(loginEvent.getEmail(), loginEvent.getPassword());
        getEventHandler().handleStateEvent(LoginEvents.SENDING_IN_PROGRESS, getClass());
    }
}
