package com.polidea.statemachine.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.R;
import com.polidea.statemachine.sample.events.StartLoginEvent;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class NotLoggedInFragment extends Fragment{

    @Inject
    Bus bus;

    public NotLoggedInFragment() {
        Application.getComponentInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_not_logged_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button loginButton = (Button) view.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new StartLoginEvent());
            }
        });
    }
}
