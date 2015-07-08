package com.polidea.statemachine.sample.di;

import android.app.Application;
import com.polidea.statemachine.sample.LoginStateableHandler;
import com.polidea.statemachine.sample.fragment.LoginFragment;
import com.polidea.statemachine.sample.fragment.NotLoggedInFragment;
import com.polidea.statemachine.sample.state.LoginInitialState;
import com.polidea.statemachine.sample.state.OnGoingLoginState;
import com.polidea.statemachine.sample.state.WaitingForLoginRequestState;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {MainModule.class})
public interface ApplicationComponent {

    void inject(NotLoggedInFragment notLoggedInFragment);

    void inject(LoginInitialState loginInitialState);

    void inject(LoginFragment loginFragment);

    void inject(OnGoingLoginState onGoingLoginState);

    void inject(WaitingForLoginRequestState waitingForLoginRequestState);

    void inject(LoginStateableHandler loginStateableHandler);

    final class Initializer {

        private Initializer() {
        }

        public static ApplicationComponent init(Application app) {
            DaggerApplicationComponent.Builder builder = DaggerApplicationComponent.builder();

            return builder
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}
