package es.deusto.sd.auctions.service;

import org.springframework.stereotype.Service;

import es.deusto.sd.auctions.dao.UsuarioRepository;
import es.deusto.sd.auctions.entity.TipoRegistro;
import es.deusto.sd.auctions.entity.Usuario;
import es.deusto.sd.auctions.external.AutorizacionGateway;
import es.deusto.sd.auctions.external.ServiceGatewayFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioAutorizacion {

	 private final UsuarioRepository usuarioRepository;
     private static Map<String, Usuario> tokenStore = new HashMap<>(); 
    
    public ServicioAutorizacion(UsuarioRepository UsuarioRepository) {
        this.usuarioRepository = UsuarioRepository;
    }
  
    
    public Optional<String> login(String email, String contrasenia) {
    	  Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
    	  String token="";
    	  TipoRegistro tr= TipoRegistro.valueOf(usuario.get().getRegistro().toString()) ;
    	  AutorizacionGateway sgf= ServiceGatewayFactory.getInstance().createGateway(tr);
    	if(sgf.validarContrasenia(email, contrasenia).get()){
            token = generateToken();  
            tokenStore.put(token, usuario.get());    
            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }
    
    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    public Usuario getUserByEmail(String email) {
		if (!usuarioRepository.findByEmail(email).isPresent()) {
			return null;
		}else {
		return usuarioRepository.findByEmail(email).get();
		}
	}
    
    public Usuario getUserByToken(String token) {
        return tokenStore.get(token); 
    }
    

    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}