/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.auctions.dao.RetoRepository;
import es.deusto.sd.auctions.dao.SesionRepository;
import es.deusto.sd.auctions.dao.UsuarioRepository;
import es.deusto.sd.auctions.entity.Deporte;
import es.deusto.sd.auctions.entity.Reto;
import es.deusto.sd.auctions.entity.Session;
import es.deusto.sd.auctions.entity.TipoRegistro;
import es.deusto.sd.auctions.entity.TipoReto;
import es.deusto.sd.auctions.entity.Usuario;
import es.deusto.sd.auctions.service.ServicioStrava;
import jakarta.transaction.Transactional;
import es.deusto.sd.auctions.service.ServicioAutorizacion;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    @Transactional
    CommandLineRunner initData(UsuarioRepository usuarioRepository, RetoRepository retoRepository, SesionRepository sesionRepository) {
		return args -> {
			if (usuarioRepository.count() > 0) {                
                return;
            }	
			// Create some users
				
			
			Usuario naroa = new Usuario("Naroa", "naroaAzcona", "1", "naroa.azcona@gmail.com",10000, 59, 168, 70,66, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);
			Usuario ander = new Usuario("Ander", "anderGonzalez", "1", "ander.gonzalez@gmail.com", 10000, 56, 80,162, 80, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.META);
			Usuario gorka = new Usuario("Gorka", "gorkaOrtuzar", "1", "gorka.ortuzar@gmail.com", 100001, 65, 170,50 ,100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);
			Usuario ainara = new Usuario("Ainara", "ainaraMaroto", "1", "ainara.maroto@gmail.com", 10000, 54, 172, 60,75, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);

			usuarioRepository.saveAll(List.of(naroa, ander, gorka, ainara));			
			
			
			logger.info("Users saved!");
			
			// Create some retos y sesiones
			
			Reto r1= new Reto("reto1", 10000,20000, Deporte.ciclismo,TipoReto.TIEMPO, ainara);
			Reto r2= new Reto("reto1", 10000,20000, Deporte.ciclismo,TipoReto.DISTANCIA, gorka);
			Reto r3= new Reto("reto1", 10000,20000, Deporte.running,TipoReto.TIEMPO, ander);
			Reto r4= new Reto("reto1", 10000,20000, Deporte.ciclismo,TipoReto.DISTANCIA, naroa);
			
			retoRepository.saveAll(List.of(r1, r2, r3,r4));
            logger.info("Retos saved!");						

			Session s1 = new Session(0, "Sesion1", Deporte.ciclismo, 70, 3000, 5000, 240, ainara);
			Session s2 = new Session(0, "Sesion2", Deporte.running, 10, 4000, 4050, 30, gorka);
			Session s3 = new Session(0, "Sesion3", Deporte.ciclismo, 100, 5000, 12000, 360, naroa);
			Session s4 = new Session(0, "Sesion4", Deporte.running, 20, 6000, 8000, 80, ander);
			
			sesionRepository.saveAll(List.of(s1, s2, s3,s4));
            logger.info("Retos saved!");
            logger.info("Sessions saved!");						
		};
	}
}