package com.polidea.statemachine;

import android.support.annotation.NonNull;
import com.polidea.statemachine.log.Logger;

public class StateChanger<PROVIDER extends StateProvider, ACTION_INTERFACE> {

    private final ACTION_INTERFACE actionInterface;

    private final PROVIDER provider;

    private final StateEventHandlerInterface eventHandler;

    private State<PROVIDER, ACTION_INTERFACE> currentState;

    public StateChanger(PROVIDER stateContext, StateEventHandlerInterface eventHandler, ACTION_INTERFACE actionInterface) {
        this.provider = stateContext;
        this.eventHandler = eventHandler;
        this.actionInterface = actionInterface;
    }

    public void setState(@NonNull State<PROVIDER, ACTION_INTERFACE> newState) {
        newState.configureState(provider, eventHandler, actionInterface);

        Logger.d("Changing state from " + currentState + " to " + newState);
        State<PROVIDER, ACTION_INTERFACE> previousState = this.currentState;
        if (previousState != null) {
            previousState.onStateLeft();
        }
        this.currentState = newState;
        this.currentState.onStateApplied();
    }

    public State<PROVIDER, ACTION_INTERFACE> getCurrentState() {
        return currentState;
    }

    public void unSetCurrentState() {
        Logger.d("Unseting state " + currentState);
        currentState.onStateLeft();
        currentState = null;
    }
}
