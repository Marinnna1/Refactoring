package ru.javaschool.JavaSchoolBackend.dao;
import org.springframework.stereotype.Repository;
import ru.javaschool.JavaSchoolBackend.entity.Treatment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TreatmentsDao {

    @PersistenceContext
    private EntityManager entityManager;



    public Treatment findTreatmentByName(String name) {
        List<Treatment> treatments = entityManager
                .createQuery("From Treatment where name = \'" + name + "\'", Treatment.class).getResultList();
        if(treatments.isEmpty()){
            return null;
        }
        return treatments.get(0);
    }
}
