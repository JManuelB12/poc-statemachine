package com.ath.springboot.backend.sagas.config;

import java.util.Random;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import com.ath.springboot.backend.sagas.common.Events;
import com.ath.springboot.backend.sagas.common.States;

public class RandomGuard implements Guard<States, Events>
{

	@Override
	public boolean evaluate(StateContext<States, Events> context)
	{
		return true;//new Random().nextBoolean();
	}

}
