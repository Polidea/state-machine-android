package com.polidea.statemachine;

public class Transition {

    private Class<?> toState;

    private int onEvent;

    public Transition(int onEvent, Class<?> toState) {
        this.toState = toState;
        this.onEvent = onEvent;
    }

    public Class<?> getToState() {
        return toState;
    }

    public int getOnEvent() {
        return onEvent;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "toState=" + toState +
                ", onEvent=" + onEvent +
                '}';
    }
}
