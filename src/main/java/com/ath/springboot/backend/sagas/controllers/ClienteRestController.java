package com.ath.springboot.backend.sagas.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ath.springboot.backend.sagas.common.Events;
import com.ath.springboot.backend.sagas.common.States;
import com.ath.springboot.backend.sagas.config.DefaultAction;
import com.ath.springboot.backend.sagas.config.DefaultErrorAction;
import com.ath.springboot.backend.sagas.config.DefaultStateMachineEventListener;
import com.ath.springboot.backend.sagas.config.RandomGuard;
import com.ath.springboot.backend.sagas.models.entity.Cliente;
import com.ath.springboot.backend.sagas.models.service.IClienteService;

@RestController
@RequestMapping("/sagas")
public class ClienteRestController
{
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
    private StateMachine<States, Events> machine1;
	
	
	@GetMapping("/cliente")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> read()
	{
		return clienteService.read();
	}

	@GetMapping("/cliente/{id}")
	public void show(@PathVariable Long id)
	{
		machine1.start();
        machine1.getExtendedState().getVariables().put("id", id);
        machine1.sendEvent(new GenericMessage<>(Events.START_STATE1, Collections.singletonMap("key", 31)));
        machine1.sendEvent(Events.STATE1_CHOICE);
        machine1.sendEvent(Events.CHOICE1_FORK);
        machine1.getExtendedState().getVariables().put("id", 2L);
        machine1.sendEvent(Events.TASK11_TASK12);
//        machine1.sendEvent(Events.TASK21_TASK22);
//        machine1.sendEvent(Events.TASK12_TASK13);
//        machine1.sendEvent(Events.TASK22_TASK23);
//        machine1.sendEvent(Events.STATE2_END);
        machine1.stop();
	}
	
	@Bean
	private Action<States, Events> consultarUsuario()
	{
		return ctx -> 
		{
			Long id;
			
			id = (Long) ctx.getExtendedState().getVariables().get("id");
			
			try
			{
				Cliente             cliente;
				
				cliente = clienteService.findById(id);
				
				if(cliente == null)
					throw new RuntimeException();
					
				System.out.println("Cliente: " + cliente.getId());
			}
			catch(DataAccessException e)
			{
				System.err.println("mensaje: Error al realizar la consulta en la base de datos");
				System.err.println("error: " + e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
				
				throw e;
			}
			catch(RuntimeException e)
			{
				System.err.println("mensaje: El cliente ID: " + id + " no existe en la base de datos!");
				
				throw e;
			}
		};
	}
	
	@Bean
	private Action<States, Events> crearUsuario()
	{
		return ctx -> 
		{
			try
			{
				Cliente             cliente = new Cliente();
				
				cliente = clienteService.create(cliente);

				System.out.println("mensaje: El cliente ha sido creado con éxito!");
				System.out.println("cliente: " + cliente);
			}
			catch(DataAccessException e)
			{
				System.err.println("mensaje: Error al realizar el insert en la base de datos");
				System.err.println("error: " + e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

				throw e;
			}
		};
	}
	
	@Bean
    public StateMachine<States, Events> buildMachine(DefaultAction action, DefaultErrorAction errorAction, DefaultStateMachineEventListener listener) throws Exception 
	{
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        {
	        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	        
	        taskScheduler.setDaemon(true);
	        taskScheduler.initialize();
	
	        builder.configureConfiguration().withConfiguration()
	                .taskScheduler(taskScheduler);
        }

        builder.configureStates()
            .withStates()
                .initial(States.START)
                .stateEntry(States.STATE1, action, errorAction)
                .stateDo(States.STATE1, action, errorAction)
                .stateExit(States.STATE1, action, errorAction)
                .choice(States.CHOICE)
                .state(States.CHOICE1)
                .state(States.CHOICE2)
                .fork(States.FORK)
                .state(States.TASKS)
                .join(States.JOIN)
                .state(States.STATE2)
                .end(States.END)
                .and()
                .withStates()
                    .parent(States.TASKS)
                    .initial(States.TASK11)
                    .state(States.TASK12)
                    .end(States.TASK13)
                .and()
                .withStates()
                    .parent(States.TASKS)
                    .initial(States.TASK21)
                    .state(States.TASK22)
                    .end(States.TASK23);

        builder.configureTransitions()
            .withExternal()
                .source(States.START)
                .target(States.STATE1)
                .event(Events.START_STATE1)
                .action(consultarUsuario(), crearUsuario())
                .and()
            .withExternal()
                .source(States.STATE1)
                .target(States.CHOICE)
                .event(Events.STATE1_CHOICE)
                .and()
            .withChoice()
                .source(States.CHOICE)
                .first(States.CHOICE1, new RandomGuard())
                .last(States.CHOICE2)
                .and()
            .withExternal()
                .source(States.CHOICE1)
                .target(States.FORK)
                .event(Events.CHOICE1_FORK)
                .and()
            .withExternal()
                .source(States.CHOICE2)
                .target(States.FORK)
                .event(Events.CHOICE2_FORK)
                .and()
            .withFork()
                .source(States.FORK)
                .target(States.TASKS)
                .and()
            .withExternal()
                .source(States.TASK11)
                .target(States.TASK12)
                .event(Events.TASK11_TASK12)
                .action(consultarUsuario())
                .and()
            .withExternal()
                .source(States.TASK12)
                .target(States.TASK13)
                .event(Events.TASK12_TASK13)
                .and()
            .withExternal()
                .source(States.TASK21)
                .target(States.TASK22)
                .event(Events.TASK21_TASK22)
                .and()
            .withExternal()
                .source(States.TASK22)
                .target(States.TASK23)
                .event(Events.TASK22_TASK23)
                .and()
            .withJoin()
                .source(States.TASK13)
                .source(States.TASK23)
                .target(States.JOIN)
                .and()
            .withExternal()
                .source(States.JOIN)
                .target(States.STATE2)
                .and()
            .withExternal()
                .source(States.STATE2)
                .target(States.END)
                .event(Events.STATE2_END);

        StateMachine<States, Events> stateMachine = builder.build();
        
        stateMachine.addStateListener(listener);
        
        return stateMachine;
    }

	@PostMapping("/cliente")
	public ResponseEntity<?> create(@RequestBody Cliente cliente)
	{
		Cliente             clienteGuardado;
		Map<String, Object> response;

		response = new HashMap<>();

		try
		{
			clienteGuardado = clienteService.create(cliente);

			response.put("mensaje", "El cliente ha sido creado con éxito!");
			response.put("cliente", clienteGuardado);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Cliente             clienteActual = null;
		Map<String, Object> response      = new HashMap<>();

		try
		{
			clienteActual = clienteService.findById(id);

			if(clienteActual == null)
			{
				response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos!");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteActual.setNombreContactoEmeregencia(cliente.getNombreContactoEmeregencia());
			clienteActual.setNumeroDocumento(cliente.getNumeroDocumento());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setTelefonoEmergencia(cliente.getTelefonoEmergencia());
			clienteActual.setTipoDocumento(cliente.getTipoDocumento());

			clienteActual = clienteService.update(clienteActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el update en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido modificado con éxito!");
		response.put("cliente", clienteActual);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();

		try
		{
			Cliente cliente = clienteService.findById(id);

			if(cliente != null)
				clienteService.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
