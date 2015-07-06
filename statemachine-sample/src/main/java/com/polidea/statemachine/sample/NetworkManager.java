package com.polidea.statemachine.sample;

import android.os.Handler;

public class NetworkManager {

    private LoginUserListener listener;

    public interface LoginUserListener {

        void loginUserSuccess();

        void loginUserFailure();
    }

    public Handler handler = new Handler();

    public void loginUser(String email, String password) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.loginUserSuccess();
            }
        }, 1500);
    }

    public void addLoginUserListener(LoginUserListener listener) {
        this.listener = listener;
    }
}
