package com.polidea.statemachine;

public abstract class State<PROVIDER extends StateProvider, ACTION_INTERFACE> {

    public abstract void onStateApplied();

    public abstract void onStateLeft();

    private PROVIDER provider;

    private StateEventHandlerInterface eventHandler;

    private ACTION_INTERFACE actionInterface;

    public void configureState(PROVIDER provider, StateEventHandlerInterface eventHandler, ACTION_INTERFACE actionInterface) {
        this.provider = provider;
        this.eventHandler = eventHandler;
        this.actionInterface = actionInterface;
    }

    public StateEventHandlerInterface getEventHandler() {
        return eventHandler;
    }

    public PROVIDER getProvider() {
        return provider;
    }

    public ACTION_INTERFACE getActionInterface() {
        return actionInterface;
    }

    protected void fireEvent(int eventId) {
        eventHandler.handleStateEvent(eventId, this.getClass());
    }
}
