package com.polidea.statemachine.handler;

import android.content.Context;
import android.os.Bundle;
import com.polidea.statemachine.StateChanger;
import com.polidea.statemachine.StateMachine;
import com.polidea.statemachine.StateProvider;
import com.polidea.statemachine.Stateable;

public abstract class BaseStateableHandler<STATE_PROVIDER extends StateProvider, ACTION_INTERFACE>
        implements Stateable<STATE_PROVIDER, ACTION_INTERFACE>, StateProvider {

    private final Context context;

    private StateMachine<STATE_PROVIDER, ACTION_INTERFACE> stateMachine;

    protected BaseStateableHandler(Context context) {
        this.context = context;
    }

    public StateChanger<STATE_PROVIDER, ACTION_INTERFACE> getStateChanger() {
        return stateMachine.getStateChanger();
    }

    @Override
    public Context provideContext() {
        return context;
    }

    public void onCreate(Bundle savedInstanceState) {
        stateMachine = new StateMachine<>(getStateProvider(), getActionInterface());
        stateMachine.setInitialStateClass(getInitialStateClass());
        onStateMachineDescribe(stateMachine);
        stateMachine.onCreate(savedInstanceState);
    }

    public void onResume() {
        stateMachine.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        stateMachine.onSaveInstanceState(outState);
    }

    public void onPause() {
        stateMachine.onPause();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public StateMachine<STATE_PROVIDER, ACTION_INTERFACE> getStateMachine() {
        return stateMachine;
    }
}
