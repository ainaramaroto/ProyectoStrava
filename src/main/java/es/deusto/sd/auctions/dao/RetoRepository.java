package es.deusto.sd.auctions.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.sd.auctions.entity.Reto;

public interface RetoRepository extends JpaRepository<Reto, Long>{
	Optional<Reto> findByid(long id);
	Optional<Reto> findBynombreReto(String nombreReto);
}
	