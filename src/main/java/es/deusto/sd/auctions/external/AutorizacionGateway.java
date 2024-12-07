package es.deusto.sd.auctions.external;

import java.util.Optional;

public interface AutorizacionGateway {
	    public Optional<Boolean> validarEmail(String email);
	    public Optional<Boolean> validarContrasenia(String email, String password);
	
}
