package com.polidea.statemachine

import spock.lang.Specification

class StateChangerTest extends Specification {

    def stateProviderMock = Mock(StateProvider)

    def eventHandlerMock = Mock(StateEventHandlerInterface)

    def interfaceMock = Mock(Object)

    def stateMock = Mock(State)

    def stateChanger = new StateChanger(stateProviderMock, eventHandlerMock, interfaceMock)

    def "set state"() {
        when:
        stateChanger.setState(stateMock)

        then:
        1 * stateMock.configureState(stateProviderMock, eventHandlerMock, interfaceMock)
        1 * stateMock.onStateApplied()
        stateChanger.getCurrentState() == stateMock
    }

    def "unset current state"() {
        setup:
        stateChanger.setState(stateMock)

        when:
        stateChanger.unSetCurrentState()

        then:
        1 * stateMock.onStateLeft()
        stateChanger.getCurrentState() == null
    }
}
