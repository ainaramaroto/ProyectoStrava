/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.deusto.sd.auctions.dao.RetoRepository;
import es.deusto.sd.auctions.dao.SesionRepository;
import es.deusto.sd.auctions.entity.Deporte;
import es.deusto.sd.auctions.entity.Reto;
import es.deusto.sd.auctions.entity.Session;
import es.deusto.sd.auctions.entity.TipoReto;
import es.deusto.sd.auctions.entity.Usuario;

@Service
public class ServicioStrava {

	private final RetoRepository retoRepository ;
	private final SesionRepository sesionRepository;
	
	private static Map<Long, Reto> mRetos = new HashMap<>();
	private static Map<Long, Session> mSesiones = new HashMap<>();

	//MÉTODOS PARA LOS RETOS
	
	public ServicioStrava(RetoRepository retoRepository,SesionRepository sesionRepository) {
		this.retoRepository = retoRepository;
		this.sesionRepository = sesionRepository;
	}
	
	public Optional<Session> getDetalleSesion(Long id) {
		    try {
		        System.out.println("Buscando sesión con ID: " + id);
		        return sesionRepository.findById(id);
		        } catch (Exception e) {
		        System.err.println("Error al buscar la sesión: " + e.getMessage());
		        throw new RuntimeException("Error al obtener detalles de la sesión", e);
		        }
    }
	
	public Optional<Reto> getDetalleReto(Long id) {
		 try {
		        System.out.println("Buscando reto con ID: " + id);
		        return retoRepository.findById(id);
		    } catch (Exception e) {
		        System.err.println("Error al buscar el reto: " + e.getMessage());
		        throw new RuntimeException("Error al obtener detalles del reto", e);
		    }
	}
	
	public List<Reto> getRetos() {
		return retoRepository.findAll();
	}
	
	public Optional<Reto> getReto(Long id) {
		return retoRepository.findByid(id);
	}
	
	public List<Usuario> getRetosPorID(long ID) {
		Optional<Reto> retos = retoRepository.findByid(ID);
		if(retos.isEmpty()) {
			 throw new RuntimeException("Retos not found");
        }
		return retos.get().getUsuariosAceptados();
	}

	public Usuario getUsuarioPorNombreUsuario(String nobre) {
		return null;
		}
	
	public boolean aceptarReto(Usuario u,Reto r) {
		if (r.getUsuariosAceptados().contains(u)) {
	        return false;
	    }
		if(r != null && !r.getUsuariosAceptados().contains(u)) {
			r.getUsuariosAceptados().add(u);
			u.getListaRetosAceptados().add(r);
			return true;
		}
		return false;
	}


	public void aniadirReto(Usuario u, String nombre, long fechainicio,long fechaFin, Deporte d, TipoReto tr) {

		Reto reto = new Reto( nombre, fechainicio, fechaFin, d, tr, u);
		u.getListadeRetosCreados().add(reto);
		retoRepository.save(reto);
	}
	
	
	//LocalDate
	public List<Reto> getRetosPorFecha(long horaIni, long horaFin){
		List<Reto> retos = new ArrayList<>();
		for(Reto r: mRetos.values()) {
			if(!(r.getFechaInicio() > horaIni) && !(r.getFechaFin() < horaFin)) {
				retos.add(r);
			}
		}
		return retos;	
	}
	
	public List<Reto> getUltimos5Reto(Usuario u) {
	    return mRetos.values().stream()
	            .filter(reto -> reto.getUsuarioCreador().equals(u)) // Filtrar retos del usuario dado
	            .sorted((r1, r2) -> Long.compare(r2.getFechaInicio(), r1.getFechaInicio())) // Ordenar por fecha de inicio descendente
	            .limit(5) // Limitar a los últimos 5
	            .collect(Collectors.toList());
		
		//HECHO CON CHATGPT
		/*Este método devuelve los últimos 5 retos creados, 
		 * ordenados por la fecha de inicio en orden descendente.
		 * */
	} 
	
	public List<Reto> getRetosAceptadosPorUsuario(Usuario u){
		List<Reto> retos = new ArrayList<>();
		for (Reto r: mRetos.values()) {
			if(r.getUsuarioCreador().equals(u)) {
				retos.add(r);
			}
		}
		return retos;
	}
	
	public List<Reto> getRetosPorDeporte(Deporte deporte) {
		List<Reto> retos = new ArrayList<>();
		for(Reto r: mRetos.values()) {
			if(r.getDeporte().equals(deporte)) {
				retos.add(r);
			}
		}
		return retos;
	}
	
	//MÉTODOS PARA LAS SESIONES
	
	public List<Session> getSesiones(){
		List<Session> lista = new ArrayList<Session>();
		for(Session s: mSesiones.values()) {
			lista.add(s);
		}
		return lista;
	}
	
	public Session getSesion(long id) {
		return mSesiones.get(id);
	}
	
	public List<Session> sesionporUsuario(Usuario u){
		List<Session> sesiones = new ArrayList<>();
		for(Session s: sesionRepository.findAll()) {
			if(s.getUsuario().equals(u)) {
				sesiones.add(s);
			}
		}
		return sesiones;
	}
	
	public List<Session> sesionesPorFecha(long horaInicio, long horaFin){
		List<Session> sesiones = new ArrayList<>();
		for(Session s: sesionRepository.findAll()) {
			if((s.getHoraInicio() == horaInicio) && (s.getHoraFin() == horaFin)) {
				sesiones.add(s);
			}
		}
		return sesiones;	
	}
	
	
	
	private long IdSesion = 0;

	public void aniadirSesion(Usuario u, Reto r,String titulo, Deporte deporte, float distancia, long horaInicio, long horaFin, float duracion) {
	    long id = IdSesion++; // Generar un nuevo id único
	    Session sesion = new Session(id, titulo, deporte, distancia, horaInicio, horaFin, duracion, r,u);
	    mSesiones.put( id, sesion);
	}
	
	public List<Session> getUltimas5SesionesporUsuario(Usuario u) {
		 return mSesiones.values().stream()
			        .filter(s -> s.getUsuario().equals(u)) // Filtrar sesiones del usuario
			        .sorted((s1, s2) -> Long.compare(s2.getHoraInicio(), s1.getHoraInicio())) // Ordenar por hora de inicio descendente
			        .limit(5) // Limitar a las últimas 5
			        .collect(Collectors.toList());
		 
		 //HECHO CON CHATGPT
	}

	public List<Session> getSesionesPorReto(Long retoId) {
	    List<Session> sesiones = new ArrayList<>();
	    for (Session s : sesionRepository.findAll()) {
	    	System.out.println("Id de el repositorio" +s.getReto().getId());
	    	System.out.println("Id metido por parametro" +retoId);
	        if (s.getReto().getId()==(retoId)) {
	            sesiones.add(s);
	        }
	    }
	    return sesiones;  
	}

} 