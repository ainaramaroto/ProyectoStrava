package es.deusto.sd.auctions.entity;

import java.util.Objects;

public class Session {
	private long id;
	private String titulo;
	private Deporte deporte;
	private float distancia;
	private long horaInicio;
	private long horaFin;
	private float duracion;
	private Usuario usuario;
	
	public Session() {
		super();
	}

	public Session(long id, String titulo, Deporte deporte, float distancia, long horaInicio, long horaFin,
			float duracion, Usuario usuario) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.duracion = duracion;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public long getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(long horaInicio) {
		this.horaInicio = horaInicio;
	}

	public long getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(long horaFin) {
		this.horaFin = horaFin;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, duracion, horaFin, horaInicio, id, titulo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		return deporte == other.deporte && Float.floatToIntBits(distancia) == Float.floatToIntBits(other.distancia)
				&& duracion == other.duracion && Objects.equals(horaFin, other.horaFin)
				&& Objects.equals(horaInicio, other.horaInicio) && id == other.id
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
	
	
	
	

}
