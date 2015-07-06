package com.polidea.statemachine.sample;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;
import com.polidea.statemachine.handler.BaseStateableHandler;
import com.polidea.statemachine.State;
import com.polidea.statemachine.StateMachine;
import com.polidea.statemachine.sample.state.LoginActionInterface;
import com.polidea.statemachine.sample.state.LoginEvents;
import com.polidea.statemachine.sample.state.LoginInitialState;
import com.polidea.statemachine.sample.state.LoginProvider;
import com.polidea.statemachine.sample.state.OnGoingLoginState;
import com.polidea.statemachine.sample.state.WaitingForLoginRequestState;
import com.polidea.statemachine.states.InitialState;

public class LoginStateableHandler extends BaseStateableHandler<LoginProvider, LoginActionInterface> implements LoginProvider, LoginActionInterface {
    private final MainActivity mainActivity;

    protected LoginStateableHandler(MainActivity delegate, Context context) {
        super(context);
        this.mainActivity = delegate;
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
        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.SENDING_IN_PROGRESS, WaitingForLoginRequestState.class);

        stateMachine.addTransitionFromClass(WaitingForLoginRequestState.class, LoginEvents.FINISHED, InitialState.class);
    }

    @Override
    public Class<? extends State> getInitialStateClass() {
        return LoginInitialState.class;
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(mainActivity, R.string.login_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError() {
        Toast.makeText(mainActivity, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginCancelled() {
        Toast.makeText(mainActivity, R.string.login_cancelled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginLoading() {
        mainActivity.progressContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginLoading() {
        mainActivity.progressContainer.setVisibility(View.GONE);
    }
}
