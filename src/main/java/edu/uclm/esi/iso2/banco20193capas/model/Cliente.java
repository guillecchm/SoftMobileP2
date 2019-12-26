package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * @author Macario.
 */
public class Cliente {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long identificador;
	@Column(unique = true)
	protected String nif;
	
	private String nombre;
	private String apellidos;

	
	public Cliente() {
	}
	
	/**
	 * Crea un cliente 
	 * @param nif	NIF del cliente
	 * @param nombre	Nombre del cliente
	 * @param apellidos	Apellidos del cliente
	 */
	public Cliente(final String nif, final String nombre, final String apellidos) {
		this.nif = nif;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	/**
	 * @return identificador.
	 */
	public final Long getId() {
		return identificador;
	}
	public final void setId(Long id) {
		this.identificador = id;
	}
	/**
	 * @return nif.
	 */
	public final String getNif() {
		return nif;
	}
	public final void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return nombre
	 */
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return apellidos
	 */
	public final String getApellidos() {
		return apellidos;
	}
	public final void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * Inserta un cliente en la base de datos
	 */
	public void insert() {
		Manager.getClienteDAO().save(this);
	}
}
