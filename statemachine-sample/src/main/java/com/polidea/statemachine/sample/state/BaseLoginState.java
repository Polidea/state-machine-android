package com.polidea.statemachine.sample.state;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.polidea.statemachine.State;
import com.polidea.statemachine.sample.fragment.LoginFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public abstract class BaseLoginState extends State<LoginProvider, LoginActionInterface> {

    protected abstract void injectDependencies();

    @Inject
    Bus bus;

    public BaseLoginState() {
        injectDependencies();
    }

    @Override
    public void onStateApplied() {
        bus.register(this);
    }

    @Override
    public void onStateLeft() {
        bus.unregister(this);
    }

    protected void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getProvider().provideFragmentManager();
        int fragmentContainerId = getProvider().provideFragmentContainerId();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainerId, fragment);
        transaction.commit();
    }

    protected void popFragment() {
        FragmentManager fragmentManager = getProvider().provideFragmentManager();
        fragmentManager.popBackStack();
    }
}
