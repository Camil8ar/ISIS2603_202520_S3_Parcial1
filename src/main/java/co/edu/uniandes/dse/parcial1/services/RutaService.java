package co.edu.uniandes.dse.parcial1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RutaService {
    
    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public RutaEntity createRuta(RutaEntity ruta) throws IllegalOperationException{
        
        log.info("Inicia proceso de creación de ruta");
        if (ruta.getNombre().isBlank() || ruta.getColor().isBlank() || ruta.getTipo().isBlank()) {
            throw new IllegalArgumentException("No se pueden ingresar textos vacíos.");
        }
            if (ruta.getTipo() != "diurna" && ruta.getTipo() != "nocturna" && ruta.getTipo() != "circular") {
            throw new IllegalArgumentException("El tipo debe ser diurna, nocturna o circular.");
        }
        return rutaRepository.save(ruta);
        }

    @Transactional
    public List<RutaEntity> getRutas() {
        log.info("Inicia proceso de consultar todas las rutas");
        return rutaRepository.findAll();
    }

    
    @Transactional
    public RutaEntity getRuta(Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la ruta con id = {0}", rutaId);
        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
                throw new EntityNotFoundException("No se encontró una ruta con el id = " + rutaId);
        log.info("Termina proceso de consultar la ruta con id = {0}", rutaId);
        return rutaEntity.get();
    } 

    @Transactional
    public RutaEntity updateRuta(Long rutaId, RutaEntity ruta)
                    throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la ruta con id = {0}", rutaId);
        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
                throw new EntityNotFoundException("No se encontró una ruta con el id = " + rutaId);
        log.info("Termina proceso de actualizar la ruta con id = {0}", rutaId);
		ruta.setId(rutaId);
		return rutaRepository.save(ruta);
	}


    @Transactional
    public void deleteRuta(Long rutaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la ruta con id = {0}", rutaId);
        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty()){
                throw new EntityNotFoundException("La ruta no existe");
        }
        rutaRepository.deleteById(rutaId);
        log.info("Termina proceso de borrar la ruta con id = {0}", rutaId);
    }
}
