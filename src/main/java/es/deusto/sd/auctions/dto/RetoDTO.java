/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.dto;

import es.deusto.sd.auctions.entity.Deporte;
import es.deusto.sd.auctions.entity.TipoReto;

public class RetoDTO {
	private Long id;
	private String nombre;
	private long fechaInicio;
	private long fechaFin;
	private TipoReto reto;
	private Deporte deporte;
	private String duracion;
	private String distancia;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public long getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(long fechaFin) {
		this.fechaFin = fechaFin;
	}
	public TipoReto getReto() {
		return reto;
	}
	public void setReto(TipoReto reto) {
		this.reto = reto;
	}
	public Deporte getDeporte() {
		return deporte;
	}
	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getDistancia() {
		return distancia;
	}
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setId(long id) {
		this.id =id;
	}
	
	

	
}