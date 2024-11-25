/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.auctions;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.auctions.entity.Deporte;
import es.deusto.sd.auctions.entity.Reto;
import es.deusto.sd.auctions.entity.TipoRegistro;
import es.deusto.sd.auctions.entity.TipoReto;
import es.deusto.sd.auctions.entity.Usuario;
import es.deusto.sd.auctions.service.ServicioStrava;
import es.deusto.sd.auctions.service.ServicioAutorizacion;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(ServicioStrava servicioStrava, ServicioAutorizacion servicioAutentificacion) {
		return args -> {			
			// Create some users
			
			Usuario naroa = new Usuario("Naroa", "naroaAzcona", "1", "naroa.azcona@gmail.com",10000, 59, 168, 70,66, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);
			Usuario ander = new Usuario("Ander", "anderGonzalez", "1", "ander.gonzalez@gmail.com", 10000, 56, 80,162, 80, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.META);
			Usuario gorka = new Usuario("Gorka", "gorkaOrtuzar", "1", "gorka.ortuzar@gmail.com", 100001, 65, 170,50 ,100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);
			Usuario ainara = new Usuario("Ainara", "ainaraMaroto", "1", "ainara.maroto@gmail.com", 10000, 54, 172, 60,75, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), TipoRegistro.GOOGLE);

			servicioAutentificacion.addUser(naroa);
			servicioAutentificacion.addUser(ander);
			servicioAutentificacion.addUser(gorka);
			servicioAutentificacion.addUser(ainara);
			
			logger.info("Users saved!");
			
			// Create some retos y sesiones
			
			servicioStrava.aniadirReto(ainara, "Reto1", 20000, 500000, Deporte.ciclismo, TipoReto.DISTANCIA);
			servicioStrava.aniadirReto(naroa, "Reto2", 10000, 20000, Deporte.running, TipoReto.TIEMPO);
			servicioStrava.aniadirReto(gorka, "Reto3", 4000, 99999, Deporte.running, TipoReto.DISTANCIA);
			servicioStrava.aniadirReto(ander, "Reto4", 555555, 40000, Deporte.ciclismo, TipoReto.TIEMPO);
			
            logger.info("Retos saved!");						

			
			servicioStrava.aniadirSesion(ainara, "Sesion 1 de ciclismo", Deporte.ciclismo, 10, 10, 12, 2);
			servicioStrava.aniadirSesion(naroa, "Sesion 4 de running", Deporte.running, 7, 9, 10, 1);
			servicioStrava.aniadirSesion(gorka, "Sesion 9 de running", Deporte.running, 10, 11, 12, 1);
			servicioStrava.aniadirSesion(ander, "Sesion 5 de ciclismo", Deporte.ciclismo, 15, 16, 19, 3);

		
            logger.info("Sessions saved!");						
		};
	}
}