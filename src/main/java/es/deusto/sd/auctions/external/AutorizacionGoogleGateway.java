package es.deusto.sd.auctions.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class AutorizacionGoogleGateway implements AutorizacionGateway {
	private static final int PORT = 8080;
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

	
}