package daniella.iths.cleancodelabb2daniellanoren.Services;

import daniella.iths.cleancodelabb2daniellanoren.DB.PatientDB;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientDB patientDB;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void getPatientById_throwExceptionIfNotFound(){
        when(patientDB.findById(anyLong())).thenReturn(Optional.empty());
        patientService.getPatientById((long)4);
        verify(patientDB.findById((long)4));
    }

    @Test
    public void getPatientById_normal(){
        Patient patient = new Patient();
        when(patientDB.findById(anyLong())).thenReturn(Optional.of(patient));
        assertEquals(patient, patientService.getPatientById((long)4));
        verify(patientDB).findById((long)4);
    }

    @Test
    public void getAllPatients_normal(){
        List<Patient> expected = new ArrayList<>(Arrays.asList(new Patient(), new Patient()));
        when(patientDB.findAll()).thenReturn(expected);
        List<Patient> actual = patientService.getAllPatients();
        verify(patientDB).findAll();
        for(int i = 0; i < actual.size(); i++){
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void saveNewPatient_normal(){
        Patient patient = new Patient();
        when(patientDB.save(patient)).thenReturn(patient);
        Patient actual = patientService.saveNewPatient(patient);
        verify(patientDB).save(patient);
        assertEquals(patient, actual);
    }

}
