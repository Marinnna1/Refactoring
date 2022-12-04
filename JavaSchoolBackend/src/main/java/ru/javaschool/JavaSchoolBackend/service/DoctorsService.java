package ru.javaschool.JavaSchoolBackend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaschool.JavaSchoolBackend.dao.DoctorDao;
import ru.javaschool.JavaSchoolBackend.entity.User;



@Service
@RequiredArgsConstructor
public class DoctorsService {

    private final DoctorDao doctorDao;


    void addDoctor(User user) {
        doctorDao.save(user);
    }
}
