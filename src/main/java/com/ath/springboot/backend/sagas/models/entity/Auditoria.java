package com.ath.springboot.backend.sagas.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Audited
public class Auditoria<U>
{
	private String operacion;
	
	@CreatedBy
	@Column(name = "id_usuario_creacion", updatable = false)
	private U idUsuarioCreacion;

	@CreatedDate
	@Column(name = "fecha_creacion", updatable = false)
	private Date fechaCreacion;

	@LastModifiedBy
	@Column(name = "id_usuario_modificacion")
	private U idUsuarioModificacion;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	public U getIdUsuarioCreacion()
	{
		return idUsuarioCreacion;
	}

	public void setIdUsuarioCreacion(U idUsuarioCreacion)
	{
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	public Date getFechaCreacion()
	{
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}

	public U getIdUsuarioModificacion()
	{
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(U idUsuarioModificacion)
	{
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	public Date getFechaModificacion()
	{
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion)
	{
		this.fechaModificacion = fechaModificacion;
	}

	@PrePersist
	public void onPrePersist()
	{
		operacion = "INSERT";
	}

	@PreUpdate
	public void onPreUpdate()
	{
		operacion = "UPDATE";
	}

	public String getOperacion()
	{
		return operacion;
	}

	public void setOperacion(String operacion)
	{
		this.operacion = operacion;
	}
}
