package com.polidea.statemachine.sample.state;

public interface LoginEvents {
    int START_LOGIN = 1;
    int CANCELLED = 2;
    int LOGIN_IN_PROGRESS = 3;
    int FINISHED = 4;
}
