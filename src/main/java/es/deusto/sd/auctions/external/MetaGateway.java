package es.deusto.sd.auctions.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class MetaGateway implements AutorizacionGateway{
	 private static final String HOST = "localhost";
	    private static final int PORT = 8081;
	    @Override
	    public Optional<Boolean> validarEmail(String email) {
	        return Optional.of(sendRequest("VALIDAR EMAIL " + email).equals("EMAIL_VALIDO"));
	    }
	    @Override
	    public Optional<Boolean> validarContrasenia(String email, String contrasenia) {
	        System.out.println(sendRequest("VALIDAR_CONTRASENIA " + email + " " + contrasenia).equals("CONTRASENIA_VALIDA"));

	        return Optional.of(sendRequest("VALIDAR_CONTRASENIA " + email + " " + contrasenia).equals("CONTRASENIA_VALIDA"));
	    }

	    
	    private String sendRequest(String request) {
	        try (Socket socket = new Socket(HOST, PORT);
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
	            out.println(request);
	            String respuesta = in.readLine();
	            System.out.println("Respuesta del servidor: " + respuesta);
	            return respuesta; // Lee la respuesta del servidor
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "ERROR";
	        }
	    }
	}
