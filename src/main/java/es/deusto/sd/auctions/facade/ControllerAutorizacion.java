/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions.facade;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.auctions.dto.CredencialesDTO;
import es.deusto.sd.auctions.dto.UsuarioDTO;
import es.deusto.sd.auctions.service.ServicioAutorizacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/autorizacion")
@Tag(name = "Controller de autorización", description = "Operaciones de registro, login y logout")
public class ControllerAutorizacion {

    private ServicioAutorizacion servicioAutorizacion;
    
	public ControllerAutorizacion(ServicioAutorizacion authService) {
		this.servicioAutorizacion = authService;
	}
    
	   
    // Login endpoint
    @Operation(
        summary = "Login to the system",
        description = "Allows a user to login by providing email and password. Returns a token if successful.",
        responses = {
        		//200 = The request succeeded
        		//401 = Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
            @ApiResponse(responseCode = "200", description = "OK: Login successful, returns a token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials, login failed"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
    		@Parameter(name = "credentials", description = "User's credentials", required = true)    	
    		@RequestBody CredencialesDTO credentials) {    	
        Optional<String> token = servicioAutorizacion.login(credentials.getEmail(), credentials.getContrasenia());
        
    	if (token.isPresent()) {
    		return new ResponseEntity<>(token.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }

    // Logout endpoint
    @Operation(
        summary = "Logout from the system",
        description = "Allows a user to logout by providing the authorization token.",
        responses = {
        		//204 = There is no content to send for this request, but the headers are useful. The user agent may update its cached headers for this resource with the new ones.
            @ApiResponse(responseCode = "204", description = "No Content: Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"),
        }
    )    
    @PostMapping("/logout")    
    public ResponseEntity<Void> logout(
    		@Parameter(name = "token", description = "Authorization token", required = true, example = "Bearer 1924888a05c")
    		@RequestBody String token) {    	
        Optional<Boolean> result = servicioAutorizacion.logout(token);
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
    }
}