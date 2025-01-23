package es.deusto.sd.auctions.entity;



import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Retos")
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JoinTable(name = "usuario_retos_aceptados",
        joinColumns = @JoinColumn(name = "reto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosAceptados = new ArrayList<>();

    // Usuarios que han completado este reto
    @ManyToMany
    @JoinTable(name = "usuario_retos_completados",
        joinColumns = @JoinColumn(name = "reto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosCompletados = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true) // Relación con Usuario
    private Usuario usuario;

    // Lista de sesiones asociadas al reto
    @OneToMany(mappedBy = "reto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sesiones = new ArrayList<>();

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

    public List<Session> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Session> sesiones) {
        this.sesiones = sesiones;
    }

    public void addSesion(Session sesion) {
        this.sesiones.add(sesion);
        sesion.setReto(this); // Actualizar la relación inversa
    }

    public void removeSesion(Session sesion) {
        this.sesiones.remove(sesion);
        sesion.setReto(null); // Eliminar la relación inversa
    }
}