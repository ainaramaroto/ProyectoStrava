package es.deusto.sd.auctions.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sesiones")
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false)
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = false)
	private Deporte deporte;
	
	@Column(nullable = false, unique = false)
	private float distancia;
	
	@Column(nullable = false, unique = false)
	private long horaInicio;
	
	@Column(nullable = false, unique = false)
	private long horaFin;
	
	@Column(nullable = false, unique = false)
	private float duracion;
	
	@ManyToOne
	@JoinColumn(name = "reto_id", nullable = true)
	private Reto reto;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	
	public Session() {
		super();
	}

	public Session(long id, String titulo, Deporte deporte, float distancia, long horaInicio, long horaFin,
			float duracion,Reto reto ,Usuario usuario) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.duracion = duracion;
		this.reto = reto;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	
	public Reto getReto() {
		return reto;
	}

	public void setReto(Reto reto) {
		this.reto = reto;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, duracion, horaFin, horaInicio, id, titulo, usuario, reto);
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
