package com.ath.springboot.backend.sagas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ath.springboot.backend.sagas.models.entity.Cliente;


public interface IClienteDao extends CrudRepository<Cliente, Long>
{

}
