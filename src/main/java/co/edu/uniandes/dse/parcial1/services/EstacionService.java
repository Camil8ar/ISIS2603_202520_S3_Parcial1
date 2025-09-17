package co.edu.uniandes.dse.parcial1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstacionService {
        
    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public EstacionEntity createEstacion(EstacionEntity estacion) throws IllegalOperationException{
        
        log.info("Inicia proceso de creación de estacion");
        if (estacion.getName().isBlank() || estacion.getDireccion().isBlank()) {
            throw new IllegalArgumentException("No se pueden ingresar textos vacíos.");
        }
        if (estacion.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        return estacionRepository.save(estacion);
        }

    @Transactional
    public List<EstacionEntity> getEstaciones() {
        log.info("Inicia proceso de consultar todas las estaciones");
        return estacionRepository.findAll();
    }

    
    @Transactional
    public EstacionEntity getEstacion(Long estacionId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la estacion con id = {0}", estacionId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
                throw new EntityNotFoundException("No se encontró una estacion con el id = " + estacionId);
        log.info("Termina proceso de consultar la estacion con id = {0}", estacionId);
        return estacionEntity.get();
    } 

    @Transactional
    public EstacionEntity updateEstacion(Long estacionId, EstacionEntity estacion)
                    throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la estacion con id = {0}", estacionId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
                throw new EntityNotFoundException("No se encontró una estacion con el id = " + estacionId);
        log.info("Termina proceso de actualizar la estacion con id = {0}", estacionId);
		estacion.setId(estacionId);
		return estacionRepository.save(estacion);
	}


    @Transactional
    public void deleteEstacion(Long estacionId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la estacion con id = {0}", estacionId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty()){
                throw new EntityNotFoundException("La estacion no existe");
        }
        estacionRepository.deleteById(estacionId);
        log.info("Termina proceso de borrar la estacion con id = {0}", estacionId);
    }
    
}
