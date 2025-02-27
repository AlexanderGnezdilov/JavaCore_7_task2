import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTest {

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("\nTest complete");
    }

    @Test
    void checkBloodPressure_test() {


        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("123", "Вальдемар", "Евлампиевич",
                        LocalDate.of(2019, 8, 10),
                        new HealthInfo(new BigDecimal(37), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);
        medicalService.checkBloodPressure("123", new BloodPressure(140, 100));


        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());

        Assertions.assertEquals("Warning, patient with id: 123, need help", argumentCaptor.getValue());

    }

    @Test
    void checkTemperature_test() {


        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("123", "Вальдемар", "Евлампиевич",
                        LocalDate.of(2019, 8, 10),
                        new HealthInfo(new BigDecimal(37), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);
        medicalService.checkTemperature("123", new BigDecimal("34.00"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123, need help", argumentCaptor.getValue());

    }

    @Test
    void indicators_are_normal() {

        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("123", "Вальдемар", "Евлампиевич",
                        LocalDate.of(2019, 8, 10),
                        new HealthInfo(new BigDecimal(37), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);

        medicalService.checkTemperature("123", new BigDecimal("36.00"));
        medicalService.checkBloodPressure("123", new BloodPressure(120, 80));

        Mockito.verify(alertServiceMock, Mockito.times(0))
                .send(Mockito.anyString());

    }

}
