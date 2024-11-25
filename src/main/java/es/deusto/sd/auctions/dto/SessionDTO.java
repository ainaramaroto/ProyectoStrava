/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.dto;

import es.deusto.sd.auctions.entity.Deporte;

public class SessionDTO {
	private String nombre;
	private Deporte deporte;
	private long inicio;
	private long fin;
	private int duracion;
	private int distancia;
	private String nombreUsuario;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getInicio() {
		return inicio;
	}
	public void setInicio(long inicio) {
		this.inicio = inicio;
	}
	public long getFin() {
		return fin;
	}
	public void setFin(long fin) {
		this.fin = fin;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	public Deporte getDeporte() {
		return deporte;
	}
	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	
	
	
	
}