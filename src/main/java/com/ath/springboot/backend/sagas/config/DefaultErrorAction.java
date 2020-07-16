package com.ath.springboot.backend.sagas.config;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.ath.springboot.backend.sagas.common.Events;
import com.ath.springboot.backend.sagas.common.States;

@Component
public class DefaultErrorAction implements Action<States, Events>
{

	@Override
	public void execute(StateContext<States, Events> context)
	{
//		context.getException().printStackTrace();
//		System.err.println("Gran vaina!");
	}

}
