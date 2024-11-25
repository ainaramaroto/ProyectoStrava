package es.deusto.sd.auctions.service;

import org.springframework.stereotype.Service;

import es.deusto.sd.auctions.dto.UsuarioDTO;
import es.deusto.sd.auctions.entity.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioAutorizacion {

    // Simulating a user repository
    private static Map<String, Usuario> userRepository = new HashMap<>();
    private static Map<String, Usuario> tokenStore = new HashMap<>(); 
    
    public Optional<String> login(String email, String contrasenia) {
        Usuario user = userRepository.get(email);
        
        if (user != null && user.checkPassword(contrasenia)) {
            String token = generateToken();  
            tokenStore.put(token, user);    

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
    
    public String registrarUsuario(UsuarioDTO uDTO) {
    	if(getUserByEmail(uDTO.getEmail()) != null) {
    		return "Email ya registrasdo";
    	}
    	
    	userRepository.put(uDTO.usuarioDTOaUsuario().getEmail(), uDTO.usuarioDTOaUsuario());
    	return "Email registrado";
    }
    
    public void addUser(Usuario user) {
    	if (user != null) {
    		userRepository.putIfAbsent(user.getEmail(), user);
    	}
    }
    
    public Usuario getUserByToken(String token) {
        return tokenStore.get(token); 
    }
    
    public Usuario getUserByEmail(String email) {
		return userRepository.get(email);
	}

    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}