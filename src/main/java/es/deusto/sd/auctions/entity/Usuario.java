/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	private String nombre;
	private String nombreUsuario;
	private String contrasenia;
	private String email;
	private long fechaNac;
	private float pesoKg;
	private int altura;
	private int frecuenciaCardiacaMax;
	private int frecuenciaCardiacaRep;
	private List<Session> listaSesionEntrenamiento = new ArrayList<>();
	private List<Reto> listadeRetosCreados = new ArrayList<>();
	private List<Reto> listaRetosCompletados = new ArrayList<>();
	private List<Reto> listaRetosAceptados = new ArrayList<>();
	private TipoRegistro registro;

	
	public boolean checkPassword(String contrasenia) {
        return this.contrasenia.equals(contrasenia);
	}
	
	// Constructor without parameters
	public Usuario() {
	}

	public Usuario(String nombre, String nombreUsuario, String contrasenia, String email, long fechaNac, float pesoKg, int altura,
			int frecuenciaCardiacaMax, int frecuenciaCardiacaRep,List<Session> listaSesionEntrenamiento, List<Reto> listadeRetosCreados,
			List<Reto> listaRetosCompletados, List<Reto> listaRetosAceptados, TipoRegistro registro) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.email = email;
		this.fechaNac = fechaNac;
		this.pesoKg = pesoKg;
		this.altura = altura;
		this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
		this.listaSesionEntrenamiento = listaSesionEntrenamiento;
		this.listadeRetosCreados = listadeRetosCreados;
		this.listaRetosCompletados = listaRetosCompletados;
		this.listaRetosAceptados = listaRetosAceptados;
		this.registro = registro;
	}



	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getFechaNac() {
		return fechaNac;
	}


	public void setFechaNac(long fechaNac) {
		this.fechaNac = fechaNac;
	}


	public float getPesoKg() {
		return pesoKg;
	}


	public void setPesoKg(float pesoKg) {
		this.pesoKg = pesoKg;
	}


	public int getAltura() {
		return altura;
	}


	public void setAltura(int altura) {
		this.altura = altura;
	}


	public int getFrecuenciaCardiacaMax() {
		return frecuenciaCardiacaMax;
	}


	public void setFrecuenciaCardiacaMax(int frecuenciaCardiacaMax) {
		this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
	}

	

	public int getFrecuenciaCardiacaRep() {
		return frecuenciaCardiacaRep;
	}

	public void setFrecuenciaCardiacaRep(int frecuenciaCardiacaRep) {
		this.frecuenciaCardiacaRep = frecuenciaCardiacaRep;
	}

	public List<Session> getListaSesionEntrenamiento() {
		return listaSesionEntrenamiento;
	}


	public void setListaSesionEntrenamiento(List<Session> listaSesionEntrenamiento) {
		this.listaSesionEntrenamiento = listaSesionEntrenamiento;
	}

	public TipoRegistro getRegistro() {
		return registro;
	}

	public void setRegistro(TipoRegistro registro) {
		this.registro = registro;
	}

	public List<Reto> getListadeRetosCreados() {
		return listadeRetosCreados;
	}

	public void setListadeRetosCreados(List<Reto> listadeRetosCreados) {
		this.listadeRetosCreados = listadeRetosCreados;
	}

	public List<Reto> getListaRetosCompletados() {
		return listaRetosCompletados;
	}

	public void setListaRetosCompletados(List<Reto> listaRetosCompletados) {
		this.listaRetosCompletados = listaRetosCompletados;
	}

	public List<Reto> getListaRetosAceptados() {
		return listaRetosAceptados;
	}

	public void setListaRetosAceptados(List<Reto> listaRetosAceptados) {
		this.listaRetosAceptados = listaRetosAceptados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(altura, contrasenia, email, fechaNac, frecuenciaCardiacaMax,frecuenciaCardiacaRep, listaRetosAceptados,
				listaRetosCompletados, listaSesionEntrenamiento, listadeRetosCreados, nombre, nombreUsuario, pesoKg,
				registro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return altura == other.altura && Objects.equals(contrasenia, other.contrasenia)
				&& Objects.equals(email, other.email) && fechaNac == other.fechaNac
				&& frecuenciaCardiacaMax == other.frecuenciaCardiacaMax
				&& frecuenciaCardiacaRep == other.frecuenciaCardiacaRep
				&& Objects.equals(listaRetosAceptados, other.listaRetosAceptados)
				&& Objects.equals(listaRetosCompletados, other.listaRetosCompletados)
				&& Objects.equals(listaSesionEntrenamiento, other.listaSesionEntrenamiento)
				&& Objects.equals(listadeRetosCreados, other.listadeRetosCreados)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Float.floatToIntBits(pesoKg) == Float.floatToIntBits(other.pesoKg) && registro == other.registro;
	}

	
	
}