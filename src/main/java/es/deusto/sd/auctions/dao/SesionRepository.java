package es.deusto.sd.auctions.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.sd.auctions.entity.Reto;
import es.deusto.sd.auctions.entity.Session;

public interface SesionRepository extends JpaRepository<Session, Long> {
	
}

