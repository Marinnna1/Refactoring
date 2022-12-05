package ru.javaschool.JavaSchoolBackend2.enums;


import org.springframework.security.core.GrantedAuthority;

public enum Position implements GrantedAuthority {
    DOCTOR,
    NURSE,
    PATIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
