/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.auctions.entity.TipoRegistro;
import es.deusto.sd.auctions.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO {
   private String email;
   private String nombreUsuario;
   private long fechaNacimiento;
   private float peso;
   private int altura;
   private int frecuenciaCaridacaMax;
   private int frecuenciaCardiacaRep;
   private TipoRegistro registro;
   private String contrasenia;
   @Schema(hidden = true)
   private List<RetoDTO> listaRetosCreadosDTO = new ArrayList<>();
   @Schema(hidden = true)
   private List<RetoDTO> listaRetosAceptadosDTO = new ArrayList<>();
   @Schema(hidden = true)
   private List<RetoDTO> listaRetosCompletadosDTO = new ArrayList<>();
   
   
	
   public UsuarioDTO() {
   }
   
   
   
   public UsuarioDTO(String email, String nombreUsuario, long fechaNacimiento, float peso, int altura,
		int frecuenciaCaridacaMax, int frecuenciaCardiacaRep, TipoRegistro registro, String contrasenia,
		List<RetoDTO> listaRetosCreadosDTO, List<RetoDTO> listaRetosAceptadosDTO,
		List<RetoDTO> listaRetosCompletadosDTO) {
	super();
	this.email = email;
	this.nombreUsuario = nombreUsuario;
	this.fechaNacimiento = fechaNacimiento;
	this.peso = peso;
	this.altura = altura;
	this.frecuenciaCaridacaMax = frecuenciaCaridacaMax;
	this.frecuenciaCardiacaRep = frecuenciaCardiacaRep;
	this.registro = registro;
	this.contrasenia = contrasenia;
	this.listaRetosCreadosDTO = listaRetosCreadosDTO;
	this.listaRetosAceptadosDTO = listaRetosAceptadosDTO;
	this.listaRetosCompletadosDTO = listaRetosCompletadosDTO;
}



public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public long getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(long fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public int getFrecuenciaCaridacaMax() {
		return frecuenciaCaridacaMax;
	}
	public void setFrecuenciaCaridacaMax(int frecuenciaCaridacaMax) {
		this.frecuenciaCaridacaMax = frecuenciaCaridacaMax;
	}
	
	public int getFrecuenciaCardiacaRep() {
		return frecuenciaCardiacaRep;
	}
	public void setFrecuenciaCardiacaRep(int frecuenciaCardiacaRep) {
		this.frecuenciaCardiacaRep = frecuenciaCardiacaRep;
	}
	public TipoRegistro getRegistro() {
		return registro;
	}
	public void setRegistro(TipoRegistro registro) {
		this.registro = registro;
	}
	public List<RetoDTO> getListaRetosCreadosDTO() {
		return listaRetosCreadosDTO;
	}
	public void setListaRetosCreadosDTO(List<RetoDTO> listaRetosCreadosDTO) {
		this.listaRetosCreadosDTO = listaRetosCreadosDTO;
	}
	public List<RetoDTO> getListaRetosAceptadosDTO() {
		return listaRetosAceptadosDTO;
	}
	public void setListaRetosAceptadosDTO(List<RetoDTO> listaRetosAceptadosDTO) {
		this.listaRetosAceptadosDTO = listaRetosAceptadosDTO;
	}
	public List<RetoDTO> getListaRetosCompletadosDTO() {
		return listaRetosCompletadosDTO;
	}
	public void setListaRetosCompletadosDTO(List<RetoDTO> listaRetosCompletadosDTO) {
		this.listaRetosCompletadosDTO = listaRetosCompletadosDTO;
	}
	
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public Usuario usuarioDTOaUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
		usuario.setNombreUsuario(this.nombreUsuario);
	    usuario.setPesoKg(this.peso);
	    usuario.setAltura(this.altura);
	    usuario.setFrecuenciaCardiacaMax(this.frecuenciaCaridacaMax);
	    usuario.setFrecuenciaCardiacaRep(this.frecuenciaCardiacaRep);
	    usuario.setRegistro(this.registro);
	    return usuario;
	}


   
   
}