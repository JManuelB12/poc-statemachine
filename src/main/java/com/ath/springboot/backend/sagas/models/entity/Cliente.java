package com.ath.springboot.backend.sagas.models.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Audited
@Table(name = "clientes")
public class Cliente extends Auditoria<String> implements Serializable
{

	private static final long serialVersionUID = 8245456844162284873L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	private String nombre;
	private String apellido;

	@Column(name = "tipo_documento")
	private String tipoDocumento;

	@Column(name = "numero_documento")
	private String numeroDocumento;
	private String direccion;
	private String telefono;

	@Column(name = "nombre_contacto_emergencia")
	private String nombreContactoEmeregencia;

	@Column(name = "telefono_emergencia")
	private String telefonoEmergencia;


	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getTipoDocumento()
	{
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento)
	{
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento()
	{
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento)
	{
		this.numeroDocumento = numeroDocumento;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getNombreContactoEmeregencia()
	{
		return nombreContactoEmeregencia;
	}

	public void setNombreContactoEmeregencia(String nombreContactoEmeregencia)
	{
		this.nombreContactoEmeregencia = nombreContactoEmeregencia;
	}

	public String getTelefonoEmergencia()
	{
		return telefonoEmergencia;
	}

	public void setTelefonoEmergencia(String telefonoEmergencia)
	{
		this.telefonoEmergencia = telefonoEmergencia;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
