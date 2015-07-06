package com.polidea.statemachine.sample.state;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.polidea.statemachine.State;
import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.NetworkManager;
import com.polidea.statemachine.sample.fragment.LoggedInFragment;
import javax.inject.Inject;

public class WaitingForLoginRequestState extends State<LoginProvider, LoginActionInterface> {

    @Inject
    NetworkManager networkManager;

    public WaitingForLoginRequestState() {
        Application.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        getActionInterface().showLoginLoading();

        networkManager.addLoginUserListener(new NetworkManager.LoginUserListener() {
            @Override
            public void loginUserSuccess() {
                getActionInterface().loginSuccess();

                showFragment(new LoggedInFragment());

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

    private void popFragment() {
        FragmentManager fragmentManager = getProvider().provideFragmentManager();
        fragmentManager.popBackStack();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getProvider().provideFragmentManager();
        int fragmentContainerId = getProvider().provideFragmentContainerId();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStateLeft() {
        getActionInterface().hideLoginLoading();
    }
}
