package com.polidea.statemachine.sample;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;
import com.polidea.statemachine.State;
import com.polidea.statemachine.StateMachine;
import com.polidea.statemachine.handler.BaseStateableHandler;
import com.polidea.statemachine.sample.events.BackStackChangedEvent;
import com.polidea.statemachine.sample.state.LoginActionInterface;
import com.polidea.statemachine.sample.state.LoginEvents;
import com.polidea.statemachine.sample.state.LoginInitialState;
import com.polidea.statemachine.sample.state.LoginProvider;
import com.polidea.statemachine.sample.state.OnGoingLoginState;
import com.polidea.statemachine.sample.state.WaitingForLoginRequestState;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class LoginStateableHandler extends BaseStateableHandler<LoginProvider, LoginActionInterface> implements LoginProvider, LoginActionInterface {

    @Inject
    Bus bus;

    private final LoginActivity mainActivity;

    protected LoginStateableHandler(LoginActivity mainActivity) {
        super(mainActivity);
        Application.getComponentInstance().inject(this);
        this.mainActivity = mainActivity;
        addOnBackStackChangedListener();
    }

    private void addOnBackStackChangedListener() {
        final FragmentManager supportFragmentManager = mainActivity.getSupportFragmentManager();
        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container);
                bus.post(new BackStackChangedEvent(currentFragment));
            }
        });
    }

    @Override
    public LoginProvider getStateProvider() {
        return this;
    }

    @Override
    public LoginActionInterface getActionInterface() {
        return this;
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return mainActivity.getSupportFragmentManager();
    }

    @Override
    public int provideFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void onStateMachineDescribe(StateMachine<LoginProvider, LoginActionInterface> stateMachine) {
        stateMachine.addTransitionFromClass(LoginInitialState.class, LoginEvents.START_LOGIN, OnGoingLoginState.class);

        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.CANCELLED, LoginInitialState.class);
        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.LOGIN_IN_PROGRESS, WaitingForLoginRequestState.class);

        stateMachine.addTransitionFromClass(WaitingForLoginRequestState.class, LoginEvents.FINISHED, LoginInitialState.class);
        stateMachine.addTransitionFromClass(WaitingForLoginRequestState.class, LoginEvents.CANCELLED, LoginInitialState.class);
    }

    @Override
    public Class<? extends State> getInitialStateClass() {
        return LoginInitialState.class;
    }

    @Override
    public void loginSuccess() {
        showMessage(R.string.login_success);
    }

    @Override
    public void loginError() {
        showMessage(R.string.login_error);
    }

    @Override
    public void loginCancelled() {
        showMessage(R.string.login_cancelled);
    }

    @Override
    public void showLoginLoading() {
        mainActivity.progressContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginLoading() {
        mainActivity.progressContainer.setVisibility(View.GONE);
    }

    public void showMessage(@StringRes int messageRes) {
        Toast.makeText(mainActivity, messageRes, Toast.LENGTH_SHORT).show();
    }
}
