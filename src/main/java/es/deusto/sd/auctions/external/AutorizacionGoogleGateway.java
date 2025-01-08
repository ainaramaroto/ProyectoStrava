package es.deusto.sd.auctions.external;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class AutorizacionGoogleGateway implements AutorizacionGateway {
	private static final int PORT = 8081;
	private static final String API_URL = "http://localhost:" + PORT + "/google/users";
    private final HttpClient httpClient;

    public AutorizacionGoogleGateway() {
    	       this.httpClient = HttpClient.newHttpClient();
    	   
    }
    @Override
	public Optional<Boolean> validarEmail(String email) {
        String url = API_URL + "?email=" + email;
        // Create a request
        try {
            // Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            // Send the request and obtain the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        	// If response is OK, parse the response body
        	if (response.statusCode() == 200) {
					return Optional.of(true);
			} else {
				return Optional.of(false);
			}
        } catch (Exception ex) {
        	return Optional.empty();
        }
	}
    @Override
	public Optional<Boolean> validarContrasenia(String email, String contrasenia) {
		
		String url = API_URL + "?email=" + email + "&contrasenia=" + contrasenia;
        try {
            // Crear el request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            // Envia el rquest para obtener la respuesta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        	if (response.statusCode() == 200) {
                    return Optional.of(true);
            } else {
                return Optional.of(false);
            }
        } catch (Exception ex) {
        	return Optional.empty();
        }  
	}

//    @Override
//    public Optional<Boolean> validarContrasenia(String email, String contrasenia) {
//        try {
//            // Codificar los parámetros
//            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
//            String encodedContrasenia = URLEncoder.encode(contrasenia, StandardCharsets.UTF_8);
//            String url = API_URL + "?email=" + encodedEmail + "&contrasenia=" + encodedContrasenia;
//
//            // Crear el cliente HTTP
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .GET()
//                    .build();
//
//            // Enviar la solicitud
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Verificar la respuesta
//            if (response.statusCode() == 200) {
//                System.out.println("Respuesta válida: " + response.body());
//                return Optional.of(true);
//            } else {
//                System.err.println("Código de estado inesperado: " + response.statusCode());
//                System.err.println("Cuerpo de la respuesta: " + response.body());
//                return Optional.of(false);
//            }
//        } catch (Exception ex) {
//            System.err.println("Error al validar la contraseña: " + ex.getMessage());
//            ex.printStackTrace();
//            return Optional.empty();
//        }
//    }

	
}