package es.deusto.sd.auctions.dao;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import es.deusto.sd.auctions.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findBynombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);

}
