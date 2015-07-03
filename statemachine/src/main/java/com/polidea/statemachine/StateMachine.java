package com.polidea.statemachine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.polidea.statemachine.log.Logger;
import com.polidea.statemachine.states.InitialState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine<PROVIDER extends StateProvider, ACTION_INTERFACE> implements StateEventHandlerInterface<PROVIDER, ACTION_INTERFACE> {

    private String currentStateClassBundleKey = "INITIAL_STATE_CLASS";

    Class<?> initialStateClass = InitialState.class;

    Map<Class<?>, List<Transition>> stateToTransitionsMap = new HashMap<>();

    StateChanger<PROVIDER, ACTION_INTERFACE> stateChanger;

    State<PROVIDER, ACTION_INTERFACE> reCreatedState;

    State<PROVIDER, ACTION_INTERFACE> unSetedState;

    public StateMachine(PROVIDER provider, ACTION_INTERFACE actionInterface) {
        stateChanger = new StateChanger<>(provider, this, actionInterface);
    }

    public StateMachine(StateChanger<PROVIDER, ACTION_INTERFACE> stateChanger) {
        this.stateChanger = stateChanger;
    }

    public void setInitialStateClass(Class<?> initialStateClass) {
        this.initialStateClass = initialStateClass;
    }

    @Override
    public void handleStateEvent(int eventId, Class<? extends State<PROVIDER, ACTION_INTERFACE>> fromStateClass) {
        List<Transition> transitions = stateToTransitionsMap.get(fromStateClass);
        if (transitions == null) {
            throw new IllegalStateException("No transitions for fromState: " + fromStateClass);
        }

        Transition foundTransition = null;
        for (Transition transition : transitions) {
            if (transition.getOnEvent() == eventId) {
                foundTransition = transition;
                break;
            }
        }

        if (foundTransition == null) {
            throw new IllegalStateException("No transition for fromState: " + fromStateClass + " eventId: " + eventId);
        }

        State<PROVIDER, ACTION_INTERFACE> newState = createStateFromClass(foundTransition.getToState());
        if (newState == null) {
            return;
        }

        Logger.i("From State: " + stateChanger.getCurrentState() + ". Transition: " + foundTransition);
        stateChanger.setState(newState);
    }

    public void addTransitionFromClass(Class<?> fromClass, int onEvent, Class<?> toClass) {
        addTransitionFromClass(fromClass, new Transition(onEvent, toClass));
    }

    public void addTransitionFromClass(Class<?> fromClass, Transition transition) {
        List<Transition> transitions = stateToTransitionsMap.get(fromClass);
        if (transitions == null) {
            transitions = new ArrayList<>();
            stateToTransitionsMap.put(fromClass, transitions);
        }
        transitions.add(transition);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        State stateToSave = null;
        if (unSetedState != null) {
            stateToSave = unSetedState;
        } else if (stateChanger.getCurrentState() != null) {
            stateToSave = stateChanger.getCurrentState();
        } else if (reCreatedState != null) {
            stateToSave = reCreatedState;
        }
        if (stateToSave != null) {
            outState.putSerializable(currentStateClassBundleKey, stateToSave.getClass());
        }
    }

    public void onPause() {
        unSetedState = stateChanger.getCurrentState();
        stateChanger.unSetCurrentState();
    }

    public void onCreate(Bundle savedInstanceState) {
        reCreateState(savedInstanceState);
    }

    public void onResume() {
        Logger.i("Resuming state");

        if (stateChanger.getCurrentState() == null) {
            configureCurrentState();
        }
    }

    public StateChanger<PROVIDER, ACTION_INTERFACE> getStateChanger() {
        return stateChanger;
    }

    public State<PROVIDER, ACTION_INTERFACE> getCurrentState() {
        return stateChanger.getCurrentState();
    }

    public void reset() {
        stateChanger.unSetCurrentState();
        reCreatedState = null;
        unSetedState = null;
        configureCurrentState();
    }

    private void reCreateState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Class<State<PROVIDER, ACTION_INTERFACE>> currentStateClass =
                    (Class<State<PROVIDER, ACTION_INTERFACE>>) savedInstanceState.getSerializable(currentStateClassBundleKey);
            try {
                reCreatedState = currentStateClass.getConstructor().newInstance();
                Logger.i("Recreating state: " + reCreatedState);
            } catch (Exception e) {
                Logger.e("Error while re creating state: " + e);
            }
        }
    }

    private void configureCurrentState() {
        if (reCreatedState != null) {
            stateChanger.setState(reCreatedState);
        } else if (unSetedState != null) {
            stateChanger.setState(unSetedState);
        } else {
            State<PROVIDER, ACTION_INTERFACE> baseState = createStateFromClass(initialStateClass);
            if (baseState == null) {
                throw new IllegalArgumentException("Cannot create state from class " + initialStateClass);
            }
            stateChanger.setState(baseState);
        }
    }

    @Nullable
    private State<PROVIDER, ACTION_INTERFACE> createStateFromClass(Class<?> stateClass) {
        State<PROVIDER, ACTION_INTERFACE> contextState = null;
        try {
            contextState = (State<PROVIDER, ACTION_INTERFACE>) stateClass.getConstructor().newInstance();
        } catch (Exception e) {
            Logger.e("Error while creating state: " + e);
        }
        return contextState;
    }

    public void setCurrentStateClassBundleKey(String currentStateClassBundleKey) {
        this.currentStateClassBundleKey = currentStateClassBundleKey;
    }
}
