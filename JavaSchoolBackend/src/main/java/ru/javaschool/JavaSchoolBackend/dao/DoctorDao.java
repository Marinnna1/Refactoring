package ru.javaschool.JavaSchoolBackend.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaschool.JavaSchoolBackend.entity.Doctor;
import ru.javaschool.JavaSchoolBackend.entity.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class DoctorDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Doctor> findDoctorByName(String name) {
        return entityManager
                .createQuery("From Doctor where user.name = \'" + name + "\'", Doctor.class).getResultList();
    }


    public void save(User user) {
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        entityManager.persist(doctor);
        entityManager.flush();

    }



}
