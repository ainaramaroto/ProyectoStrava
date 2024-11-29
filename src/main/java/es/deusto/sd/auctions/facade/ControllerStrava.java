package es.deusto.sd.auctions.facade;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.auctions.dto.UsuarioDTO;
import es.deusto.sd.auctions.dto.RetoDTO;
import es.deusto.sd.auctions.dto.SessionDTO;
import es.deusto.sd.auctions.entity.Deporte;
import es.deusto.sd.auctions.entity.Reto;
import es.deusto.sd.auctions.entity.Session;
import es.deusto.sd.auctions.entity.Usuario;
import es.deusto.sd.auctions.service.ServicioStrava;
import es.deusto.sd.auctions.service.ServicioAutorizacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/strava")
@Tag(name = "Controller de Strava", description = "Operaciones relacionadas con la visualización y creacion de retos y sesiones")
public class ControllerStrava {

	private final ServicioAutorizacion servicioAutorizacion;
	private final ServicioStrava servicioStrava;

	public ControllerStrava(ServicioStrava servicioStrava, ServicioAutorizacion authService) {
		this.servicioStrava = servicioStrava;
		this.servicioAutorizacion = authService;
	}

	
	// GET de todas las sesiones de cada usuario
		@Operation(summary = "Get todas las sesiones de cada usuario",description = "Devuelve una lista de todas las sesiones de entrenamiento que tiene cada usuario.",responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of sessions retrieved successfully"),
				@ApiResponse(responseCode = "204", description = "No Content: No sessions found"),
				@ApiResponse(responseCode = "500", description = "Internal server error")})
		@GetMapping("/sesion/usuario")
		public ResponseEntity<List<SessionDTO>> getSesionesUsuario(
				@Parameter(name = "token", description = "User's token", required = true)    	
				@RequestParam("token") String token) {
			try {
				Usuario u = servicioAutorizacion.getUserByToken(token);
				if(u == null) {
		            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				// pregunta
				List<Session> sesiones = servicioStrava.sesionporUsuario(u);
				
				if (sesiones == null || sesiones.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				
				List<SessionDTO> sesionDTOs = sesiones.stream().map(this::sessionToDTO).collect(Collectors.toList());
				
				return new ResponseEntity<>(sesionDTOs, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//Crear una sesion de entrenamiento
	@Operation(summary = "Crear una nueva sesión de entrenamiento",description = "Permite a un usuario crear manualmente una sesión.",responses = {
	        @ApiResponse(responseCode = "201", description = "Created: Session created successfully"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")})
	@PostMapping("/sessions/crear")
	public ResponseEntity<Void> crearSesion(
	        @RequestParam("token") String token, 
	        @RequestBody SessionDTO session) {
	        Usuario u = servicioAutorizacion.getUserByToken(token);
	        if(u == null) {
	        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	        servicioStrava.aniadirSesion(u, token, session.getDeporte(), session.getDistancia(), session.getInicio(), session.getFin(), session.getDuracion());
	        return new ResponseEntity<>(HttpStatus.CREATED);
		
	    }
	
	//Obtener una sesión por fecha
	@Operation(summary = "Obtener una sesión por fecha",description = "Devuelve una lista de sesiones de formación dentro de un rango de fechas especificado", responses = {
	        @ApiResponse(responseCode = "200", description = "OK: List of sessions retrieved successfully"),
	        @ApiResponse(responseCode = "204", description = "No Content: No sessions found"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token")})
	@GetMapping("/sessions/fecha")
	public ResponseEntity<List<SessionDTO>> getSesionporFecha(
	        @RequestParam("token") String token, 
	        @RequestParam("inicio") long inicio,
	        @RequestParam("fin") long fin) {
		Usuario u = servicioAutorizacion.getUserByToken(token);
		if(u== null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<Session> sesiones = servicioStrava.sesionesPorFecha(inicio, fin);
		if(sesiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<SessionDTO> sesionDTOs = new ArrayList<>();
		for (Session sesion : sesiones) {
		    SessionDTO sesionDTO = sessionToDTO(sesion);
		    sesionDTOs.add(sesionDTO);
		}
        return new ResponseEntity<>(sesionDTOs, HttpStatus.OK);
	    }
	

	// Crear un Reto
	@Operation(summary = "Crear un reto",description = "Crear un nuevo reto",responses = {
			@ApiResponse(responseCode = "201", description = "Created: Challenge created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	 
	@PostMapping("/reto/crear")
	public ResponseEntity<List<UsuarioDTO>> crearReto(
			@RequestParam("token") String token,
			@RequestBody RetoDTO reto) {
		
			Usuario u = servicioAutorizacion.getUserByToken(token);
			if(u == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			servicioStrava.aniadirSesion(u, token, reto.getDeporte(), Float.parseFloat(reto.getDistancia()), 
					reto.getFechaInicio(), reto.getFechaFin(), Float.parseFloat(reto.getDuracion()));
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Obtener todos los retos
	@Operation(summary = "Obtener todos los retos",description = "Devuelve una lista con todos los retos disponibles.",responses = {
	        @ApiResponse(responseCode = "200", description = "OK: Retos obtenidos correctamente"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
	@GetMapping("/retos")
    public ResponseEntity<List<RetoDTO>> getRetos() {
		try {
            List<Reto> retos = servicioStrava.getRetos();
            List<RetoDTO> retoDTOs = new ArrayList<>();
            for (Reto reto : retos) {
                RetoDTO retoDTO = retoToDTO(reto);
                retoDTOs.add(retoDTO);
            }
            return new ResponseEntity<>(retoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}
	
	//Obtener retos activos
	
	@Operation(summary = "Obtener retos activos ",description = "Obtenemos la lista de retos activos",responses = {
			@ApiResponse(responseCode = "200", description = "OK: Challenges retrieved successfully"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
	        @ApiResponse(responseCode = "500", description = "Internal server error")})
	 
	@GetMapping("/retos/activos")
	public ResponseEntity<List<RetoDTO>> getRetosActivos(
			@RequestParam("token") String token){
		
			Usuario u = servicioAutorizacion.getUserByToken(token);
			if(u == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			List<Reto> retos = servicioStrava.getUltimos5Reto(u);
			if(retos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			List<RetoDTO> challengeDTOs = new ArrayList<>();
			for (Reto reto : retos) {
			    challengeDTOs.add(retoToDTO(reto));
			}
			return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);

		
	}

	// Aceptar un reto
	@Operation(summary = "Aceptar un reto", description = "Permite a un usuario aceptar un reto específico", responses = {
		    @ApiResponse(responseCode = "200", description = "OK: Challenge accepted successfully"),
		    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
		    @ApiResponse(responseCode = "404", description = "Not Found: Challenge not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)		
	@PutMapping("/reto/{retoID}")
	public ResponseEntity<Void> aceptarReto(
	        @RequestParam("token") String token,
	        @PathVariable("idReto") long idReto){	    	
	    	Usuario u = servicioAutorizacion.getUserByToken(token);
	    	
	    	if (u == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
	    	
	    	Reto r = servicioStrava.getReto(idReto);
	    	if (r == null) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
	    	
			boolean aceptado = servicioStrava.aceptarReto(u, r);
			if(aceptado) {
		        return new ResponseEntity<>(HttpStatus.OK);

			}
	        
			return new ResponseEntity<>(HttpStatus.CONFLICT);
	   
	    
	}
	
	@Operation(summary = "Obtener los retos aceptados por un usuario", description = "Este método obtiene la lista de los retos que un usuario ha aceptado.", responses = {
		    @ApiResponse(responseCode = "200", description = "OK: Retos aceptados recuperados correctamente"),
		    @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido"),
		    @ApiResponse(responseCode = "404", description = "Not Found: Usuario no encontrado"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")})
	@GetMapping("/reto/retosAceptados")
	public ResponseEntity<List<RetoDTO>> getRetosAceptados(
			@Parameter(name = "Token", description = "Token del usuario", required = true) 
			@RequestParam("Token") String token)  {
			Usuario u = servicioAutorizacion.getUserByToken(token);
			if( u == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			List<Reto> retos =  servicioStrava.getRetosAceptadosPorUsuario(u);
			if(retos.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			List<RetoDTO> retosDTO = retos.stream().map(this::retoToDTO).collect(Collectors.toList());
		    return new ResponseEntity<>(retosDTO, HttpStatus.OK);

	}
	
	//Obtener reto por fecha
	@Operation(summary = "Obtener reto por fecha", description = "RDevuelve una lista de retos dentro de un rango de fechas específico.", responses = {
			@ApiResponse(responseCode = "200", description = "OK: List of challenges retrieved successfully"),
			@ApiResponse(responseCode = "204", description = "No Content: No challenges found")})
	@GetMapping("/reto/porfecha")
	public ResponseEntity<List<RetoDTO>> getRetoByDate(
			@RequestParam("token") String token,
			@RequestParam("inicio") long fechaInicio,
            @RequestParam("fin") long fechaFin) {
		
			Usuario u = servicioAutorizacion.getUserByToken(token);
			if(u== null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			List<Reto> retos = servicioStrava.getRetosPorFecha(fechaInicio, fechaFin);
			if(retos.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			List<RetoDTO> retoDTOs = new ArrayList<>();
			for (Reto reto : retos) {
			    RetoDTO retoDTO = retoToDTO(reto);
			    retoDTOs.add(retoDTO);
			}
	        return new ResponseEntity<>(retoDTOs, HttpStatus.OK);

	}
	
	//Añadir un reto
	@Operation(summary = "Añadir un nuevo reto", description = "Permite a un usuario crear un nuevo reto.", responses = {
			@ApiResponse(responseCode = "201", description = "Created: Reto creado correctamente"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Usuario no autenticado"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor")})
	@PostMapping("/reto/aniadir")
	public ResponseEntity<Void> aniadirReto(
			@RequestParam("token") String token,
			@RequestBody RetoDTO retoDTO) {
			Usuario u = servicioAutorizacion.getUserByToken(token);
	        if (u == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	        
	        servicioStrava.aniadirReto(u, retoDTO.getNombre(), retoDTO.getFechaInicio(), retoDTO.getFechaFin(), retoDTO.getDeporte(), retoDTO.getReto());
	        return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Operation(summary = "Obtener retos por deporte", description = "Devuelve una lista de retos filtrados por el deporte especificado (CICLISMO o RUNNING).", responses = {
	        @ApiResponse(responseCode = "200", description = "OK: Retos obtenidos correctamente"),
	        @ApiResponse(responseCode = "400", description = "Bad Request: El deporte no es válido"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized: Usuario no autenticado"),
	        @ApiResponse(responseCode = "404", description = "Not Found: No se encontraron retos para el deporte"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	        }
	    )
	@GetMapping("/reto/pordeporte")
	public ResponseEntity<List<RetoDTO>> getRetosPorDeporte(
	        @RequestParam("token") String token,
	        @RequestParam("deporteStr") String deporteStr) {
			Usuario u = servicioAutorizacion.getUserByToken(token);
			if (u == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			Deporte deporte = Deporte.valueOf(deporteStr.toLowerCase());			
			List<Reto> retos = servicioStrava.getRetosPorDeporte(deporte);
			if(retos.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			List<RetoDTO> retoDTOs = retos.stream().map(this::retoToDTO).collect(Collectors.toList());
	        return new ResponseEntity<>(retoDTOs, HttpStatus.OK);	
			
	}
	
	
	
	//pasar de entity a DTO
	
	private RetoDTO retoToDTO(Reto reto) {
	    RetoDTO dto = new RetoDTO();
	    dto.setNombre(reto.getNombreReto());
	    dto.setFechaInicio(reto.getFechaInicio());
	    dto.setFechaFin(reto.getFechaFin());
	    dto.setDeporte(reto.getDeporte());
	    dto.setReto(reto.getTipoReto());

	    return dto;
	}
	
	private SessionDTO sessionToDTO(Session session) {
	    SessionDTO sessionDTO = new SessionDTO();
	    sessionDTO.setNombre(session.getTitulo());  
	    sessionDTO.setDeporte(session.getDeporte());  
	    sessionDTO.setDistancia((int) session.getDistancia());  
	    sessionDTO.setInicio(session.getHoraInicio());  
	    sessionDTO.setFin(session.getHoraFin()); 
	    sessionDTO.setDuracion((int) session.getDuracion());  
	    sessionDTO.setNombreUsuario(session.getUsuario().getEmail()); 
	    
	    return sessionDTO;
	}

}