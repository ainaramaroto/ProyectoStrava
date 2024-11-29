package es.deusto.sd.auctions.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import es.deusto.sd.auctions.entity.Session;

public interface SesionRepository extends JpaRepository<Session, Long> {

}
