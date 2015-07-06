# State Machine for Android

Lightweight state machine implementation that is used in Polidea.

## Download

//TODO

## Usage

The easiest way to use StateMachine is to extend `BaseStateableHandler`. It have 4 main methods that we should override:

* `getStateProvider()` - used by State's to get data from our Fragment/Activity. Base StateProvider contains one method `provideContext()`
* `getActionInterface()` - used by State's to perform action's on Activity/Fragment, e.g. show Toast.
* `getInitialStateClass()` - initial state class for `StateMachine`. By default it returns `InitialState` class.
* `onStateMachineDescribe(StateMachine stateMachine)` - here we should describe transitions between states in our `StateMachine`. Each transition is a set of: from class, to class, event id. It means: when current state is 'from state' and it will propagate 'event id' then state machine should go to 'to state'.

Sample `BaseStateableHandler`:

```
public class LoginStateableHandler extends BaseStateableHandler<LoginProvider, LoginActionInterface> implements LoginProvider, LoginActionInterface {
    @Override
    public LoginProvider getStateProvider() {
        return this;
    }

    @Override
    public LoginActionInterface getActionInterface() {
        return this;
    }

    @Override
    public int provideFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void onStateMachineDescribe(StateMachine<LoginProvider, LoginActionInterface> stateMachine) {
        stateMachine.addTransitionFromClass(LoginInitialState.class, LoginEvents.START_LOGIN, OnGoingLoginState.class);

        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.CANCELLED, LoginInitialState.class);
        stateMachine.addTransitionFromClass(OnGoingLoginState.class, LoginEvents.SENDING_IN_PROGRESS, WaitingForLoginRequestState.class);

        stateMachine.addTransitionFromClass(WaitingForLoginRequestState.class, LoginEvents.FINISHED, InitialState.class);
    }

    @Override
    public Class<? extends State> getInitialStateClass() {
        return LoginInitialState.class;
    }

    ...
}
```

You must remember to call handler `onCreate(Bundle savedInstanceState)`, `onResume()`, `onPause()`, `onSaveInstanceState(Bundle outState)` methods in appropriate Fragment/Activity lifecycle method's. Sample usage:

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ...
    handler = new LoginStateableHandler();
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
```

Each state should extends `State` that contains two methods:

* `onStateApplied()` - called when entering state
* `onStateLeft()` - called when leaving state

Each state should call `fireEvent(int eventId)` when it finish it's job. Sample `State`:

```
public class OnGoingLoginState extends State<LoginProvider, LoginActionInterface>{

    @Inject
    Bus bus;

    @Inject
    NetworkManager networkManager;

    public OnGoingLoginState() {
        Application.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        bus.register(this);
    }

    @Override
    public void onStateLeft() {
        bus.unregister(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        networkManager.loginUser(loginEvent.getEmail(), loginEvent.getPassword());
        fireEvent(LoginEvents.SENDING_IN_PROGRESS);
    }
}
```

## Used libraries

* **[spock]** https://code.google.com/p/spock/
* **[android support library v7]**

## LICENSE

[LICENSE](./LICENSE)