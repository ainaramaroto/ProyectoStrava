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
  
    
    
 
    
    public Optional<String> login(String email, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
//
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();// Obtener el usuario
            System.out.println("Usuario encontrado");
            // Crear el gateway
            TipoRegistro registro = usuario.getRegistro();
            System.out.println(registro);
            Optional<Boolean> esContraseniaValida = ServiceGatewayFactory.getInstance()
                .createGateway(registro)
                .validarContrasenia(email, password);
            	System.out.println(esContraseniaValida);
            // Comprobar si la contraseña es válida
            if (esContraseniaValida.isPresent() && esContraseniaValida.get()) {
                String token = generateToken(); 
                tokenStore.put(token, usuario); 
                return Optional.of(token); 
            } else {
                System.out.println("Contraseña inválida o no se pudo validar.");
                return Optional.empty(); 
            }
        } else {
            System.out.println("Usuario no encontrado.");
            return Optional.empty(); 
        }
    }
//     
    	    // Buscar al usuario por email
//    	    Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
//
//    	    return usuarioOptional.flatMap(usuario -> {
//    	        // Crear el gateway en base al registro del usuario
//    	        TipoRegistro registro = usuario.getRegistro();
//    	        Optional<Boolean> esContraseniaValida = ServiceGatewayFactory.getInstance()
//    	            .createGateway(registro)
//    	            .validarContrasenia(email, password);
//    	        	System.out.println("hola1");
//    	        	System.out.println("Valor de esContraseniaValida: " + esContraseniaValida);
//    	        // Comprobar si la contraseña es válida
//    	        if (esContraseniaValida.filter(Boolean::booleanValue).isPresent()) {
//    	        	System.out.println("hola2");
//    	            String token = generateToken();
//    	            tokenStore.put(token, usuario);
//    	            return Optional.of(token);
//    	        } else {
//    	        	 System.out.println("Contraseña inválida o no se pudo validar.");
//    	            return Optional.empty();
//    	        }
//    	    }).or(() -> {
//    	    	System.out.println("Usuario no encontrado.");
//    	        return Optional.empty();
//    	    });
//
//    }

    
//    public Optional<String> login(String email, String contrasenia) {
//    	  Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
//    	  String token="";
//    	  Titr= TipoRegistro.valueOf(usuario.get().getRegistro()) ;
//    	  AutorizacionGateway sgf= ServiceGatewayFactory.getInstance().createGateway(tr);
//    	if(sgf.validarContrasenia(email, contrasenia).orElse(false)){
//            token = generateToken();  
//            tokenStore.put(token, usuario.get());    
//            return Optional.of(token);
//        } else {
//        	return Optional.empty();
//        }
//    }
    
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