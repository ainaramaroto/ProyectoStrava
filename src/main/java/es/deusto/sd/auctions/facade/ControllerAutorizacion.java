package es.deusto.sd.auctions.facade;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.auctions.dto.CredencialesDTO;
import es.deusto.sd.auctions.service.ServicioAutorizacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/autorizacion")
@Tag(name = "Controller de autorización", description = "Operaciones de registro, login y logout")
public class ControllerAutorizacion {

    private final ServicioAutorizacion servicioAutorizacion;

    public ControllerAutorizacion(ServicioAutorizacion authService) {
        this.servicioAutorizacion = authService;
    }

    // Endpoint de Login
    @Operation(
        summary = "Login en el sistema",
        description = "Permite a un usuario iniciar sesión proporcionando su correo electrónico y contraseña. Devuelve un token si es exitoso.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Inicio de sesión exitoso, devuelve un token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Credenciales inválidas, inicio de sesión fallido"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(name = "credentials", description = "Credenciales del usuario", required = true)
            @RequestBody CredencialesDTO credentials) {

        Optional<String> token = servicioAutorizacion.login(credentials.getEmail(), credentials.getContrasenia());

        return token.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    // Endpoint de Logout
    @Operation(
        summary = "Logout del sistema",
        description = "Permite a un usuario cerrar sesión proporcionando el token de autorización.",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content: Cierre de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, cierre de sesión fallido"),
        }
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(name = "token", description = "Token de autorización", required = true, example = "Bearer 1924888a05c")
            @RequestBody String token) {

        Optional<Boolean> result = servicioAutorizacion.logout(token);

        return result.filter(Boolean::booleanValue)
                     .map(r -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                     .orElseGet(() -> new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED));
    }
}
