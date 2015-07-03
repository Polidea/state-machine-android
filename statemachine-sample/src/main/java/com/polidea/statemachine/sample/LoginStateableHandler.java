package com.polidea.statemachine.sample;

import android.content.Context;
import com.polidea.statemachine.BaseStateableHandler;
import com.polidea.statemachine.State;
import com.polidea.statemachine.StateMachine;
import com.polidea.statemachine.sample.state.LoginActionInterface;
import com.polidea.statemachine.sample.state.LoginEvents;
import com.polidea.statemachine.sample.state.LoginInitialState;
import com.polidea.statemachine.sample.state.LoginProvider;
import com.polidea.statemachine.sample.state.OnGoingLoginState;
import com.polidea.statemachine.sample.state.SendLoginRequestState;
import com.polidea.statemachine.sample.state.WaitingForLoginRequestState;
import com.polidea.statemachine.states.InitialState;

public class LoginStateableHandler extends BaseStateableHandler<LoginProvider, LoginActionInterface> implements LoginProvider, LoginActionInterface {

    public interface Delegate {

        void loginSuccess();

        void loginError();

        void loginCancelled();

    }

    private final Delegate delegate;

    protected LoginStateableHandler(Delegate delegate, Context context) {
        super(context);
        this.delegate = delegate;
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
    public void onStateMachineDescribe(StateMachine<LoginProvider, LoginActionInterface> stateMachine) {
        stateMachine.addTransitionFromClass(LoginInitialState.class, LoginEvents.START_LOGIN, OnGoingLoginState.class);

        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.CANCELLED, LoginInitialState.class);
        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.SEND_LOGIN_REQUEST, SendLoginRequestState.class);

        stateMachine.addTransitionFromClass(SendLoginRequestState.class, LoginEvents.SENDING_IN_PROGRESS, WaitingForLoginRequestState.class);

        stateMachine.addTransitionFromClass(WaitingForLoginRequestState.class, LoginEvents.FINISHED, InitialState.class);
    }

    @Override
    public Class<? extends State> getInitialStateClass() {
        return LoginInitialState.class;
    }

    @Override
    public void loginSuccess() {
        delegate.loginSuccess();
    }

    @Override
    public void loginError() {
        delegate.loginError();
    }

    @Override
    public void loginCancelled() {
        delegate.loginCancelled();
    }
}
