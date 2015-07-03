package com.polidea.statemachine.states;


import com.polidea.statemachine.State;
import com.polidea.statemachine.StateProvider;

public class InitialState<PROVIDER extends StateProvider, VIEW_INTERFACE> extends State<PROVIDER, VIEW_INTERFACE> {

    @Override
    public void onStateApplied() {

    }

    @Override
    public void onStateLeft() {

    }
}
