package es.deusto.sd.auctions.facade;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
		
		@Operation(summary = "Obtener detalles de una sesión",
			    description = "Devuelve los detalles completos de una sesión específica por su ID.",
			    responses = {
			        @ApiResponse(responseCode = "200", description = "OK: Sesión encontrada y devuelta correctamente"),
			        @ApiResponse(responseCode = "404", description = "Not Found: Sesión no encontrada"),
			        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
			    }
			)
			@GetMapping("/sesion/{sesionId}")
			public ResponseEntity<SessionDTO> getDetalleSesion(
			    @PathVariable(name = "sesionId") Long sesionId) {
			    try {
			        // Obtener la sesión del servicio
			        Optional<Session> sesion = servicioStrava.getDetalleSesion(sesionId);
			        
			        if (sesion.isEmpty()) {
			            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			        }

			        // Convertir la sesión a DTO
			        SessionDTO sesionDTO = sessionToDTO(sesion.get());
			        return new ResponseEntity<>(sesionDTO, HttpStatus.OK);
			        
			    } catch (Exception e) {
			        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			}
		
		
	
		//Obtener todos los retos
		@Operation(summary = "Obtener todos las sesiones",description = "Devuelve una lista con todos las sesiones.",responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Sesiones obtenidos correctamente"),
		        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
		@GetMapping("/sesiones")
	    public ResponseEntity<List<SessionDTO>> getSesiones() {
			try {
	            List<Session> sesiones = servicioStrava.getSesiones();
	            List<SessionDTO> sesionDTOs = new ArrayList<>();
	            for (Session sesion : sesiones) {
	                SessionDTO sesionDTO = sessionToDTO(sesion);
	                sesionDTOs.add(sesionDTO);
	            }
	            return new ResponseEntity<>(sesionDTOs, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }

		}
		
		
		@Operation(summary = "Obtener sesiones de un reto",
			    description = "Devuelve una lista con todas las sesiones asociadas a un reto específico.",
			    responses = {
			        @ApiResponse(responseCode = "200", description = "OK: Sesiones obtenidas correctamente"),
			        @ApiResponse(responseCode = "404", description = "Reto no encontrado"),
			        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
			    }
			)
			@GetMapping("/retos/{retoID}/sesiones")
			public ResponseEntity<List<SessionDTO>> getSesionesPorReto(
			    @PathVariable(name = "retoID") Long retoID) {  // Especificamos el nombre explícitamente
			    try {
			        // Obtener las sesiones del reto a través del servicio
			        List<Session> sesiones = servicioStrava.getSesionesPorReto(retoID);

			        if (sesiones == null || sesiones.isEmpty()) {
			            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			        }

			        // Convertir las sesiones a DTOs
			        List<SessionDTO> sesionDTOs = sesiones.stream()
			            .map(this::sessionToDTO)
			            .collect(Collectors.toList());

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
	@PostMapping("/retos/{retoId}/sesiones")
	public ResponseEntity<Void> crearSesion(
	    @PathVariable Long retoId,
	    @RequestBody Session sesion,
	    @RequestParam("token") String token) {
	    try {
	        System.out.println("Creando sesión para reto: " + retoId);
	        System.out.println("Datos de sesión: " + sesion);
	        
	        // Obtener el usuario usando el token
	        Usuario usuario = servicioAutorizacion.getUserByToken(token);
	        if (usuario == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	        
	        // Obtener el reto usando el ID
	        Optional<Reto> reto = servicioStrava.getDetalleReto(retoId);
	        if (reto.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        servicioStrava.aniadirSesion(
	            usuario,
	            reto.get(),
	            token,
	            sesion.getDeporte(), 
	            sesion.getDistancia(), 
	            sesion.getHoraInicio(), 
	            sesion.getHoraFin(), 
	            sesion.getDuracion()
	        );
	        
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (Exception e) {
	        System.err.println("Error al crear sesión: " + e.getMessage());
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
	



	//Obtener todos los retos
	@Operation(summary = "Obtener todos los retos",description = "Devuelve una lista con todos los retos disponibles.",responses = {
	        @ApiResponse(responseCode = "200", description = "OK: Retos obtenidos correctamente"),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
	@GetMapping("/retos")
    public ResponseEntity<List<RetoDTO>> getRetos() {
		try {
            List<Reto> retos = servicioStrava.getRetos();
            List<RetoDTO> retoDTOs = retos.stream()
                    .map(this::retoToDTO)
                    .collect(Collectors.toList());
            
            
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

	
	@Operation(summary = "Aceptar un reto", 
	          description = "Permite a un usuario aceptar un reto específico", 
	          responses = {
	              @ApiResponse(responseCode = "200", description = "OK: Challenge accepted successfully"),
	              @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
	              @ApiResponse(responseCode = "404", description = "Not Found: Challenge not found"),
	              @ApiResponse(responseCode = "409", description = "Conflict: Challenge already accepted"),
	              @ApiResponse(responseCode = "500", description = "Internal server error")
	          }
	)
	@PutMapping("/reto/{retoId}")
	public ResponseEntity<Void> aceptarReto(
	        @RequestParam("token") String token,
	        @PathVariable("retoId") Long retoId) {
	    
	    // Verificar usuario
	    Usuario u = servicioAutorizacion.getUserByToken(token);
	    if (u == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }

	    // Obtener y verificar el reto
	    Optional<Reto> optionalReto = servicioStrava.getReto(retoId);
	    if (optionalReto.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    // Intentar aceptar el reto
	    try {
	        boolean aceptado = servicioStrava.aceptarReto(u, optionalReto.get());
	        if (aceptado) {
	            return new ResponseEntity<>(HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.CONFLICT);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping("/reto/{retoId}")
	public ResponseEntity<RetoDTO> getDetallesReto(
	    @PathVariable(name = "retoId") Long retoId) {
	    try {
	        // Obtener los detalles del reto del servicio
	        Optional<Reto> reto = servicioStrava.getDetalleReto(retoId);

	        if (reto.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        // Convertir el reto a DTO
	        RetoDTO retoDTO = retoToDTO(reto.get());
	        return new ResponseEntity<>(retoDTO, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
	    if (reto.getId() != 0) {  // Asegurarnos de que hay un ID
	        dto.setId(reto.getId());
	    }
	    dto.setNombre(reto.getNombreReto());
	    dto.setFechaInicio(reto.getFechaInicio());
	    dto.setFechaFin(reto.getFechaFin());
	    dto.setDeporte(reto.getDeporte());
	    dto.setReto(reto.getTipoReto());

	    return dto;
	}
	
	private SessionDTO sessionToDTO(Session session) {
	    SessionDTO sessionDTO = new SessionDTO();
	    sessionDTO.setId(session.getId());
	    sessionDTO.setTitulo(session.getTitulo());  
	    sessionDTO.setDeporte(session.getDeporte());  
	    sessionDTO.setDistancia((int) session.getDistancia());  
	    sessionDTO.setInicio(session.getHoraInicio());  
	    sessionDTO.setFin(session.getHoraFin()); 
	    sessionDTO.setDuracion((int) session.getDuracion());  
	    if (session.getUsuario() != null) {
	        sessionDTO.setNombreUsuario(session.getUsuario().getEmail());
	    }
	    
	    return sessionDTO;
	}

}