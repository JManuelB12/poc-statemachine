package com.ath.springboot.backend.sagas.config;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ath.springboot.backend.sagas.common.Events;
import com.ath.springboot.backend.sagas.common.States;

@Component
public class DefaultAction implements Action<States, Events>
{
	private static Logger logger = LoggerFactory.getLogger(DefaultAction.class);

	@Override
	public void execute(StateContext<States, Events> context)
	{
//		logger.info(
//				"Action Source: {}, State: {}, Target: {}, Event: {}",
//				(context.getSource() != null) ? context.getSource().getId() : null,
//				context.getStateMachine().getState().getId(),
//				(context.getTarget() != null) ? context.getTarget().getId() : null, context.getEvent()
//		);
	}

}
