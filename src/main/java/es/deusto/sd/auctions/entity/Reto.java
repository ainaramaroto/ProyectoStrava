package es.deusto.sd.auctions.entity;

import java.util.List;
import java.util.Objects;

public class Reto  {
	private long id;
	private String nombreReto;
	private long fechaInicio;
	private long fechaFin;
	private Deporte deporte;
	private TipoReto tipoReto;
	private Usuario usuario;
	private List<Usuario> usuarios;
	
	public Reto() {
		super();
	}

	public Reto(long id, String nombreReto, long fechaInicio, long fechaFin, Deporte deporte, TipoReto tipoReto, Usuario usuario,
			 List<Usuario> usuarios) {
		super();
		this.id = id;
		this.nombreReto = nombreReto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.deporte = deporte;
		this.tipoReto = tipoReto;
		this.usuario = usuario;
		this.usuarios = usuarios;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public TipoReto getTipoReto() {
		return tipoReto;
	}

	public void setTipoReto(TipoReto tipoReto) {
		this.tipoReto = tipoReto;
	}	

	public String getNombreReto() {
		return nombreReto;
	}

	public void setNombreReto(String nombreReto) {
		this.nombreReto = nombreReto;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, fechaFin, fechaInicio, id, nombreReto, tipoReto, usuario, usuarios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reto other = (Reto) obj;
		return deporte == other.deporte && fechaFin == other.fechaFin && fechaInicio == other.fechaInicio
				&& id == other.id && Objects.equals(nombreReto, other.nombreReto) && tipoReto == other.tipoReto
				&& Objects.equals(usuario, other.usuario) && Objects.equals(usuarios, other.usuarios);
	}
	
	

	

	
	
	
	
	
	
	
	

}
