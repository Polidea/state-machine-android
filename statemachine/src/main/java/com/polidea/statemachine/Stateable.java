package com.polidea.statemachine;

public interface Stateable<STATE_PROVIDER extends StateProvider, ACTION_INTERFACE> {

    STATE_PROVIDER getStateProvider();

    ACTION_INTERFACE getActionInterface();

    void onStateMachineDescribe(StateMachine<STATE_PROVIDER, ACTION_INTERFACE> stateMachine);

    StateMachine<STATE_PROVIDER, ACTION_INTERFACE> getStateMachine();

    StateChanger<STATE_PROVIDER, ACTION_INTERFACE> getStateChanger();

    Class<? extends State> getInitialStateClass();
}
