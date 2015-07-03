package com.polidea.statemachine;

public interface StateEventHandlerInterface<PROVIDER extends StateProvider, VIEW_INTERFACE> {

    void handleStateEvent(int eventId, Class<? extends State<PROVIDER, VIEW_INTERFACE>> fromStateClass);
}
