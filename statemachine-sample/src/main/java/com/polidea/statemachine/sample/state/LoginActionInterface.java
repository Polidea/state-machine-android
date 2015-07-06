package com.polidea.statemachine.sample.state;

public interface LoginActionInterface {

    void loginSuccess();

    void loginError();

    void loginCancelled();

    void showLoginLoading();

    void hideLoginLoading();
}
