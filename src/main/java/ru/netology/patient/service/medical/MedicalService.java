package ru.netology.patient.service.medical;

import ru.netology.patient.entity.BloodPressure;

import java.math.BigDecimal;

public interface MedicalService {

    void checkBloodPressure(String patientId, BloodPressure bloodPressure);

    void checkTemperature(String patientId, BigDecimal temperature);
}
