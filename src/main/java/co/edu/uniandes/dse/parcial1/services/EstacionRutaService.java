package co.edu.uniandes.dse.parcial1.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstacionRutaService {
    
    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    /**
     * Agrega una ruta existente a la estación
     * @throws IllegalOperationException 
     */
    @Transactional
    public RutaEntity addEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar una ruta a la estacion con id = {0}", estacionId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);

        if (estacionEntity.isEmpty())
            throw new EntityNotFoundException("Estacion no encontrada");

        if (rutaEntity.isEmpty())
            throw new EntityNotFoundException("Ruta no encontrada");

        /**
         * if ((rutaEntity.get().getEstaciones()).contains(estacionEntity.get()));
            throw new IllegalOperationException("Ruta no encontrada");
         */
        
        estacionEntity.get().getRutas().add(rutaEntity.get());
        rutaEntity.get().getEstaciones().add(estacionEntity.get());

        log.info("Termina proceso de asociar una ruta a la estacion con id = {0}", estacionId);
        return rutaEntity.get();
    }

    @Transactional
    public void removeEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un ruta del estacion con id = {}", estacionId);
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
            throw new EntityNotFoundException("Estacion no encontrada");

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
            throw new EntityNotFoundException("Ruta no encontrada");

        /**
         * if (!((rutaEntity.get().getEstaciones()).contains(estacionEntity.get())));
            throw new IllegalOperationException("Ruta no contiene estacion");
         */

        estacionEntity.get().getRutas().remove(rutaEntity.get());
        rutaEntity.get().getEstaciones().remove(estacionEntity.get());
        log.info("Finaliza proceso de borrar un ruta del estacion con id = {}", estacionId);
    }
}
