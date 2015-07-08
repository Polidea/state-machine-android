package com.polidea.statemachine.sample.di;

import android.app.Application;
import com.polidea.statemachine.sample.manager.NetworkManager;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MainModule {

    private final Application application;

    public MainModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Bus provideBus() {
        return new Bus();
    }

    @Singleton
    @Provides
    NetworkManager provideNetworkManager() {
        return new NetworkManager();
    }
}
