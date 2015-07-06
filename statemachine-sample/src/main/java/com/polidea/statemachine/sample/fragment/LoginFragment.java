package com.polidea.statemachine.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.polidea.statemachine.sample.Application;
import com.polidea.statemachine.sample.R;
import com.polidea.statemachine.sample.events.LoginEvent;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class LoginFragment extends Fragment{

    @Inject
    Bus bus;

    public LoginFragment() {
        Application.getComponentInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText emailEditText = (EditText) view.findViewById(R.id.email);
        final EditText passwordEditText = (EditText) view.findViewById(R.id.password);

        Button loginButton = (Button) view.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new LoginEvent(emailEditText.getText().toString(), passwordEditText.getText().toString()));
            }
        });
    }
}
