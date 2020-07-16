package com.ath.springboot.backend.sagas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.ath.springboot.backend.sagas.common.Events;
import com.ath.springboot.backend.sagas.common.States;

@Component
public class DefaultStateMachineEventListener extends StateMachineListenerAdapter<States, Events>
{
	private static Logger logger = LoggerFactory.getLogger(DefaultStateMachineEventListener.class);

	@Override
	public void stateChanged(State<States, Events> from, State<States, Events> to)
	{
//		logger.info(
//				"Listener stateChanged from {} to {}", (from != null) ? from.getId() : null,
//				(to != null) ? to.getId() : null
//		);
	}

	@Override
	public void stateEntered(State<States, Events> state)
	{
//		logger.info("Listener stateEntered in {}", state.getId());
	}

	@Override
	public void stateExited(State<States, Events> state)
	{
//		logger.info("Listener stateExited in {}", state.getId());
	}

	@Override
	public void transition(Transition<States, Events> transition)
	{
//		logger.info(
//				"Listener transition from {} to {}",
//				(transition.getSource() != null) ? transition.getSource().getId() : null,
//				(transition.getTarget() != null) ? transition.getTarget().getId() : null
//		);
	}

	@Override
	public void transitionStarted(Transition<States, Events> transition)
	{
//		logger.info(
//				"Listener transitionStarted from {} to {}",
//				(transition.getSource() != null) ? transition.getSource().getId() : null,
//				(transition.getTarget() != null) ? transition.getTarget().getId() : null
//		);
	}

	@Override
	public void transitionEnded(Transition<States, Events> transition)
	{
//		logger.info(
//				"Listener transitionEnded from {} to {}",
//				(transition.getSource() != null) ? transition.getSource().getId() : null,
//				(transition.getTarget() != null) ? transition.getTarget().getId() : null
//		);
	}

	@Override
	public void stateMachineStarted(StateMachine<States, Events> stateMachine)
	{
//		logger.info("Listener stateMachineStarted");
	}

	@Override
	public void stateMachineStopped(StateMachine<States, Events> stateMachine)
	{
//		logger.info("Listener stateMachineStopped");
	}

	@Override
	public void eventNotAccepted(Message<Events> event)
	{
//		logger.info("Listener eventNotAccepted {}", event);
	}

	@Override
	public void extendedStateChanged(Object key, Object value)
	{
//		logger.info("Listener extendedStateChanged {} to {}", key, value);
	}

	@Override
	public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception)
	{
		stateMachine.stop();
//		logger.info("Listener stateMachineError {}", exception.getMessage());
	}

	@Override
	public void stateContext(StateContext<States, Events> stateContext)
	{
//		logger.info(
//				"Listener stateContext (Message Payload: {}, Message Headers: {}, Variables: {}",
//				(stateContext.getMessage() != null) ? stateContext.getMessage().getPayload() : null,
//				stateContext.getMessageHeaders(), stateContext.getExtendedState().getVariables()
//		);
	}
}
