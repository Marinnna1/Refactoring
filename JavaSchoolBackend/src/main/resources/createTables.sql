CREATE TABLE treatment(id SERIAL PRIMARY KEY, name TEXT, type TEXT);

CREATE TABLE user(id SERIAL PRIMARY KEY, name TEXT, password TEXT, token TEXT, position TEXT);

CREATE TABLE doctor(id SERIAL PRIMARY KEY, FOREIGN KEY(user_id) REFERENCES user(id));

CREATE TABLE patient(id SERIAL PRIMARY KEY, name TEXT, diagnosis TEXT, insurance INT, status TEXT,
email TEXT);

CREATE table patient_doctor(FOREIGN KEY(patient_id) REFERENCES patient(id), FOREIGN KEY(doctor_id)
REFERENCES doctor(id));

CREATE TABLE appointments(id SERIAL PRIMARY KEY, FOREIGN KEY(patient_id) REFERENCES patient(id),
FOREIGN KEY(treatment_id) REFERENCES treatment(id), time_pattern TEXT, dose DOUBLE, start_date DATE,
end_date DATE);

CREATE TABLE events(id SERIAL PRIMARY KEY, date DATE, status TEXT, reason TEXT, FOREIGN KEY
(appointment_id) REFERENCES appointment(id));
