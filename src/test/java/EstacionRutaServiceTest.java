import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.services.Autowired;
import co.edu.uniandes.dse.parcial1.services.EstacionRutaService;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EstacionRutaService.class)
public class EstacionRutaServiceTest {
    @Autowired
    private EstacionRutaServiceTest estacionRutaServiceTest;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private EstacionEntity estacion = new EstacionEntity();
    private List<RutaEntity> rutaList = new ArrayList<>();

    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from RutaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstacionEntity").executeUpdate();
    }

        private void insertData() {
        estacion = factory.manufacturePojo(EstacionEntity.class);
        entityManager.persist(estacion);

        for (int i = 0; i < 3; i++) {
            co.edu.uniandes.dse.parcial1.entities.RutaEntity ruta = factory.manufacturePojo(RutaEntity.class);
            entityManager.persist(ruta);
            rutaList.add(ruta);
            estacion.getRutas().add(ruta);

        }
    }
}