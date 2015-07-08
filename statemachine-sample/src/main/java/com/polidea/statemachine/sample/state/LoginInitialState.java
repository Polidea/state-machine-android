package com.polidea.statemachine.sample.state;

import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.events.StartLoginEvent;
import com.polidea.statemachine.sample.fragment.LoginFragment;
import com.squareup.otto.Subscribe;

public class LoginInitialState extends BaseLoginState {


    @Override
    protected void injectDependencies() {
        Application.getComponentInstance().inject(this);
    }

    @Subscribe
    public void onStartLoginEvent(StartLoginEvent startLoginEvent) {
        changeFragment(new LoginFragment());

        fireEvent(LoginEvents.START_LOGIN);
    }
}
