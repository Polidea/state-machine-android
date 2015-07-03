package com.polidea.statemachine.sample.state;

public interface LoginEvents {
    int START_LOGIN = 1;
    int CANCELLED = 2;
    int SEND_LOGIN_REQUEST = 3;
    int SENDING_IN_PROGRESS = 4;
    int FINISHED = 5;
}
