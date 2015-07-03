package com.polidea.statemachine.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.polidea.statemachine.sample.fragment.LoginFragment;
import com.polidea.statemachine.sample.fragment.NotLoggedInFragment;
import com.polidea.statemachine.sample.fragment.LoggedInFragment;

public class MainActivity extends AppCompatActivity implements LoginStateableHandler.Delegate {

    boolean loggedIn = false;

    LoginStateableHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(loggedIn) {
            showFragment(new LoggedInFragment());
        } else {
            showFragment(new NotLoggedInFragment());
        }

        handler = new LoginStateableHandler(this, this);
        handler.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        handler.onSaveInstanceState(outState);
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginError() {

    }

    @Override
    public void loginCancelled() {

    }

    public void loginButtonClicked() {
        showFragment(new LoginFragment());

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment.getClass().isInstance(currentFragment)) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
