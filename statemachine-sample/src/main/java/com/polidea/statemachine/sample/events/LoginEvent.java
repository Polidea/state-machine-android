package com.polidea.statemachine.sample.events;

public class LoginEvent {

    private final String email, password;

    public LoginEvent(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
