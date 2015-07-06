package com.polidea.statemachine

import android.os.Bundle
import com.polidea.statemachine.states.InitialState
import com.polidea.statemachine.states.SampleFirstState
import com.polidea.statemachine.states.SampleSecondState
import spock.lang.Specification

class StateMachineTest extends Specification {

    def stateChangerMock = Mock(StateChanger)

    def stateMachine = new StateMachine(stateChangerMock)

    def "should handle state event"() {
        setup:
        stateMachine.addTransitionFromClass(SampleFirstState.class, 100, SampleSecondState.class);

        when:
        stateMachine.handleStateEvent(100, SampleFirstState.class);

        then:
        1 * stateChangerMock.setState(_ as SampleSecondState)
    }

    def "should correctly add transition from class"() {
        when:
        stateMachine.addTransitionFromClass(SampleFirstState.class, 100, SampleSecondState.class);
        List<Transition> transitionList = stateMachine.stateToTransitionsMap.get(SampleFirstState.class) as List<Transition>

        then:
        transitionList.size() == 1
        transitionList.get(0).getOnEvent() == 100
        transitionList.get(0).getToState() == SampleSecondState.class
    }

    def "on save instance state when unseted state"() {
        setup:
        def outStateMock = Mock(Bundle.class)

        and:
        stateMachine.setCurrentStateClassBundleKey("bundle_key")
        stateMachine.unSetedState = Mock(SampleFirstState.class)

        when:
        stateMachine.onSaveInstanceState(outStateMock)

        then:
        1 * outStateMock.putSerializable("bundle_key", stateMachine.unSetedState.getClass())
    }

    def "on save instance state when current state"() {
        setup:
        def outStateMock = Mock(Bundle.class)

        and:
        def currentStateMock = Mock(SampleFirstState)
        stateChangerMock.getCurrentState() >> currentStateMock

        and:
        stateMachine.setCurrentStateClassBundleKey("bundle_key")

        when:
        stateMachine.onSaveInstanceState(outStateMock)

        then:
        1 * outStateMock.putSerializable("bundle_key", currentStateMock.getClass())
    }

    def "on save instance state when re created state"() {
        setup:
        def outStateMock = Mock(Bundle.class)

        and:
        stateMachine.setCurrentStateClassBundleKey("bundle_key")
        stateMachine.reCreatedState = Mock(SampleFirstState)

        when:
        stateMachine.onSaveInstanceState(outStateMock)

        then:
        1 * outStateMock.putSerializable("bundle_key", stateMachine.reCreatedState.getClass())
    }

    def "on pause"() {
        setup:
        def currentStateMock = Mock(SampleFirstState)
        stateChangerMock.getCurrentState() >> currentStateMock

        when:
        stateMachine.onPause()

        then:
        stateMachine.unSetedState == currentStateMock
        1 * stateChangerMock.unSetCurrentState()
    }

    def "on create"() {
        setup:
        Bundle savedInstanceStateMock = Mock(Bundle)
        savedInstanceStateMock.getSerializable("bundle_key") >> SampleFirstState.class

        and:
        stateMachine.setCurrentStateClassBundleKey("bundle_key")

        when:
        stateMachine.onCreate(savedInstanceStateMock)

        then:
        stateMachine.reCreatedState.getClass() is SampleFirstState
    }

    def "on resume re created state"() {
        setup:
        def stateMock = Mock(SampleFirstState)
        stateMachine.reCreatedState = stateMock

        when:
        stateMachine.onResume()

        then:
        1 * stateChangerMock.setState(stateMock)

    }

    def "on resume un seted state"() {
        setup:
        def stateMock = Mock(SampleFirstState)
        stateMachine.unSetedState = stateMock

        when:
        stateMachine.onResume()

        then:
        1 * stateChangerMock.setState(stateMock)
    }

    def "on resume"() {
        when:
        stateMachine.onResume()

        then:
        1 * stateChangerMock.setState(_ as InitialState)
    }

    def "reset"() {
        setup:
        stateMachine.unSetedState = Mock(State)
        stateMachine.reCreatedState = Mock(State)

        when:
        stateMachine.reset()

        then:
        1 * stateChangerMock.unSetCurrentState()
        stateMachine.unSetedState == null
        stateMachine.reCreatedState == null
    }
}
