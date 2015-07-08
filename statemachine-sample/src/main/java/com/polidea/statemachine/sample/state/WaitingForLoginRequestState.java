package com.polidea.statemachine.sample.state;

import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.events.BackStackChangedEvent;
import com.polidea.statemachine.sample.fragment.LoggedInFragment;
import com.polidea.statemachine.sample.manager.NetworkManager;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class WaitingForLoginRequestState extends BaseLoginState {

    @Inject
    NetworkManager networkManager;

    @Override
    protected void injectDependencies() {
        Application.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        super.onStateApplied();

        getActionInterface().showLoginLoading();

        networkManager.setLoginUserListener(new NetworkManager.LoginUserListener() {
            @Override
            public void loginUserSuccess() {
                getActionInterface().loginSuccess();

                changeFragment(new LoggedInFragment());

                fireEvent(LoginEvents.FINISHED);
            }

            @Override
            public void loginUserFailure() {
                getActionInterface().loginError();

                popFragment();

                fireEvent(LoginEvents.FINISHED);
            }
        });
    }

    @Override
    public void onStateLeft() {
        super.onStateLeft();

        networkManager.setLoginUserListener(null);
        getActionInterface().hideLoginLoading();
    }

    @Subscribe
    public void onBackStackChangedEvent(BackStackChangedEvent backStackChangedEvent) {
        fireEvent(LoginEvents.CANCELLED);
    }
}
