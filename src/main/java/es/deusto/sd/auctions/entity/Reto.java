package es.deusto.sd.auctions.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "Retos")
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = false)
    private String nombreReto;

    @Column(nullable = false, unique = false)
    private long fechaInicio;

    @Column(nullable = false, unique = false)
    private long fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    private Deporte deporte;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    private TipoReto tipoReto;

    // Usuario creador del reto
    @ManyToOne
    @JoinColumn(name = "usuario_creador_id", nullable = false)
    private Usuario usuarioCreador;

    // Usuarios que han aceptado este reto
    @ManyToMany
    @JoinTable(name = "usuario_retos_aceptados",joinColumns = @JoinColumn(name = "reto_id"),inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosAceptados = new ArrayList<>();

    // Usuarios que han completado este reto
    @ManyToMany
    @JoinTable(name = "usuario_retos_completados",joinColumns = @JoinColumn(name = "reto_id"),inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosCompletados = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true) // Relación con Usuario
    private Usuario usuario;

    // Constructor vacío
    public Reto() {}

    // Constructor con parámetros
    public Reto(String nombreReto, long fechaInicio, long fechaFin, Deporte deporte, TipoReto tipoReto, Usuario usuarioCreador) {
        this.nombreReto = nombreReto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.deporte = deporte;
        this.tipoReto = tipoReto;
        this.usuarioCreador = usuarioCreador;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreReto() {
        return nombreReto;
    }

    public void setNombreReto(String nombreReto) {
        this.nombreReto = nombreReto;
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

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public List<Usuario> getUsuariosAceptados() {
        return usuariosAceptados;
    }

    public void setUsuariosAceptados(List<Usuario> usuariosAceptados) {
        this.usuariosAceptados = usuariosAceptados;
    }

    public List<Usuario> getUsuariosCompletados() {
        return usuariosCompletados;
    }

    public void setUsuariosCompletados(List<Usuario> usuariosCompletados) {
        this.usuariosCompletados = usuariosCompletados;
    }
}
