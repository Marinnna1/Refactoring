package ru.javaschool.JavaSchoolBackend.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javaschool.JavaSchoolBackend.dao.DoctorDao;
import ru.javaschool.JavaSchoolBackend.entity.User;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DoctorsServiceTest {

    @Mock
    DoctorDao doctorDao;

    @InjectMocks
    DoctorsService doctorsService;

    @Test
    @DisplayName("Save doctor test")
    void addDoctorTest() {
        User user = new User();
        doctorsService.addDoctor(user);
        verify(doctorDao, times(1)).save(user);
    }
}
