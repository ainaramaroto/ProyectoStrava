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

            // Crear las listas de retos para cada usuario
            List<Reto> retosGorka = new ArrayList<>();
            List<Reto> retosAnder = new ArrayList<>();
            List<Reto> retosNaroa = new ArrayList<>();
            List<Reto> retosAinara = new ArrayList<>();
            
            // Crear usuarios
            Usuario naroa = new Usuario("Naroa", "naroaAzcona", "1", "naroa.azcona@gmail.com", 
                10000, 59, 168, 70, 66, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
                retosNaroa, TipoRegistro.GOOGLE);
            
            Usuario ander = new Usuario("Ander", "anderGonzalez", "1", "ander.gonzalez@gmail.com", 
                10000, 56, 80, 162, 80, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
                retosAnder, TipoRegistro.META);
            
            Usuario gorka = new Usuario("Gorka", "gorkaOrtuzar", "1", "gorka.ortuzar@gmail.com", 
                100001, 65, 170, 50, 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
                retosGorka, TipoRegistro.GOOGLE);
            
            Usuario ainara = new Usuario("Ainara", "ainaraMaroto", "1", "ainara.maroto@gmail.com", 
                10000, 54, 172, 60, 75, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
                retosAinara, TipoRegistro.GOOGLE);
            
            // Guardar usuarios
            usuarioRepository.saveAll(List.of(naroa, ander, gorka, ainara));
            logger.info("Usuarios guardados!");

            // Crear retos para Gorka
            Reto retoGorka1 = new Reto("Vuelta a Bizkaia", 50000, 100000, Deporte.ciclismo, TipoReto.DISTANCIA, gorka);
            Reto retoGorka2 = new Reto("Maratón de montaña", 30000, 40000, Deporte.running, TipoReto.TIEMPO, gorka);
            Reto retoGorka3 = new Reto("Reto semanal cycling", 15000, 25000, Deporte.ciclismo, TipoReto.DISTANCIA, gorka);
           
            retosGorka.addAll(List.of(retoGorka1, retoGorka2, retoGorka3));

            // Crear retos para Ander
            Reto retoAnder1 = new Reto("Media maratón", 20000, 30000, Deporte.running, TipoReto.TIEMPO, ander);
            Reto retoAnder2 = new Reto("Tour de Francia", 80000, 150000, Deporte.ciclismo, TipoReto.DISTANCIA, ander);
            Reto retoAnder3 = new Reto("Reto mensual running", 25000, 35000, Deporte.running, TipoReto.DISTANCIA, ander);
            retosAnder.addAll(List.of(retoAnder1, retoAnder2, retoAnder3));

            // Crear retos para Naroa
            Reto retoNaroa1 = new Reto("10K diario", 10000, 15000, Deporte.running, TipoReto.DISTANCIA, naroa);
            Reto retoNaroa2 = new Reto("Entrenamiento intensivo", 20000, 25000, Deporte.ciclismo, TipoReto.TIEMPO, naroa);
            Reto retoNaroa3 = new Reto("Reto de primavera", 30000, 40000, Deporte.running, TipoReto.DISTANCIA, naroa);
            retosNaroa.addAll(List.of(retoNaroa1, retoNaroa2, retoNaroa3));

            // Crear retos para Ainara
            Reto retoAinara1 = new Reto("Desafío montaña", 40000, 60000, Deporte.ciclismo, TipoReto.DISTANCIA, ainara);
            Reto retoAinara2 = new Reto("Preparación maratón", 35000, 45000, Deporte.running, TipoReto.TIEMPO, ainara);
            Reto retoAinara3 = new Reto("Reto verano", 25000, 35000, Deporte.ciclismo, TipoReto.DISTANCIA, ainara);
            retosAinara.addAll(List.of(retoAinara1, retoAinara2, retoAinara3));

            // Guardar todos los retos
            retoRepository.saveAll(retosGorka);
            retoRepository.saveAll(retosAnder);
            retoRepository.saveAll(retosNaroa);
            retoRepository.saveAll(retosAinara);
            logger.info("Retos guardados!");
            
            ainara.getListaRetosAceptados().addAll(retosAinara);
            naroa.getListaRetosAceptados().addAll(retosNaroa);
            gorka.getListaRetosAceptados().addAll(retosGorka);
            ander.getListaRetosAceptados().addAll(retosAnder);

            // Crear sesiones (mantenemos las existentes y añadimos más)
            Session s1 = new Session(0, "Sesión montaña", Deporte.ciclismo, 70, 3000, 5000, 240, retoAinara1,ainara);
            Session s2 = new Session(0, "Entrenamiento suave", Deporte.running, 10, 4000, 4050, 30,retoGorka1 ,gorka);
            Session s3 = new Session(0, "Gran Fondo", Deporte.ciclismo, 100, 5000, 12000, 360,retoNaroa1 ,naroa);
            Session s4 = new Session(0, "Intervalo intensivo", Deporte.running, 20, 6000, 8000, 80, retoAnder2,ander);
            Session s5 = new Session(0, "Sesión montaña", Deporte.ciclismo, 70, 3000, 5000, 240, retoAinara1,gorka);
            Session s6 = new Session(0, "Entrenamiento suave asfalto", Deporte.running, 10, 4000, 4050, 30,retoGorka1 ,ainara);
            Session s7 = new Session(0, "Gran Fondo", Deporte.ciclismo, 100, 5000, 12000, 360,retoNaroa1 ,ander);
            Session s8 = new Session(0, "Intervalo intensivo", Deporte.running, 20, 6000, 8000, 80, retoAnder2,naroa);
            sesionRepository.saveAll(List.of(s1, s2, s3, s4));
            logger.info("Sesiones guardadas!");

            List<Session> SesionesRetoAinara1 = new ArrayList<>();
            SesionesRetoAinara1.add(s1);
            SesionesRetoAinara1.add(s5);

            retoAinara1.setSesiones(SesionesRetoAinara1);
            s1.setReto(retoAinara1);

           

            // Mostrar información guardada
            logger.info("Retos totales: " + retoRepository.findAll());
            logger.info("Sesiones totales: " + sesionRepository.findAll());
            logger.info("Usuarios totales: " + usuarioRepository.findAll());
        };
    }
}