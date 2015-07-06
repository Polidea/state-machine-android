package com.polidea.statemachine.sample;

import com.polidea.statemachine.sample.di.ApplicationComponent;

public class Application extends android.app.Application {

    private static Application instance;

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        component = ApplicationComponent.Initializer.init(this);
    }

    public static ApplicationComponent getComponentInstance() {
        return instance.component;
    }
}
