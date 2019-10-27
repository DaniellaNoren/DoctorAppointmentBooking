package daniella.iths.cleancodelabb2daniellanoren.Controllers;

import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidEntityException;
import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import daniella.iths.cleancodelabb2daniellanoren.Services.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController = new PatientController();

    @Mock
    private PatientService patientService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll_normal(){
        List<Patient> patients = new ArrayList<>(Arrays.asList(new Patient(),
                new Patient(),
                new Patient()));

        when(patientService.getAllPatients()).thenReturn(patients);
        List<Patient> actual = (List<Patient>)patientController.getAllPatients().getBody();
        verify(patientService).getAllPatients();

        for(int i = 0; i < patients.size(); i++){
            assertEquals(patients.get(0), actual.get(0));
        }
    }

    @Test
    public void getAll_empty(){
        List<Patient> patients = (List<Patient>)patientController.getAllPatients().getBody();
        verify(patientService).getAllPatients();
        assertEquals(0, patients.size());
    }

    @Test
    public void getPatient_normal(){
        Patient expected = new Patient();
        when(patientService.getPatientById((long)5)).thenReturn(expected);
        Patient actual = (Patient) patientController.getPatient((long)5).getBody();
        verify(patientService).getPatientById((long)5);
        assertEquals(expected, actual);
    }

    @Test
    public void getPatient_return404StatusWhenExceptionThrown(){
        when(patientService.getPatientById(anyLong())).thenThrow(EntityDoesNotExistException.class);
        ResponseEntity actual = patientController.getPatient((long)4);
        verify(patientService).getPatientById((long)4);
        assertEquals(404, actual.getStatusCodeValue());
    }

    @Test
    public void createPatient_returnPatientWhenCreated(){
        Patient expected = new Patient();
        when(patientService.saveNewPatient(expected)).thenReturn(expected);
        Patient actual = (Patient) patientController.createNewPatient(expected).getBody();
        verify(patientService).saveNewPatient(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void createPatient_return500StatusWhenExceptionThrown(){
        Patient expected = new Patient();
        when(patientService.saveNewPatient(expected)).thenThrow(InvalidEntityException.class);
        ResponseEntity actual = patientController.createNewPatient(expected);
        verify(patientService).saveNewPatient(expected);
        assertEquals(500, actual.getStatusCodeValue());
    }

    @Test
    public void delete_return200StatusWhenNormal(){
        Patient expected = new Patient();
        when(patientService.getPatientById(anyLong())).thenReturn(expected);
        doAnswer((i) -> { assertEquals(expected, i.getArgument(0)); return null;} ).when(patientService).deletePatient(expected);
        ResponseEntity actual = patientController.deletePatient((long) 4);
        verify(patientService).getPatientById((long) 4);
        verify(patientService).deletePatient(expected);
        assertEquals(200, actual.getStatusCodeValue());
    }

    @Test
    public void delete_return404StatusWhenPatientDoesNotExist(){
        when(patientService.getPatientById(anyLong())).thenThrow(EntityDoesNotExistException.class);
        ResponseEntity actual = patientController.deletePatient((long) 4);
        verify(patientService).getPatientById((long) 4);
        assertEquals(404, actual.getStatusCodeValue());
    }

    @Test
    public void update_returnAppointmentWhenNormal(){
        Patient expectedNew = new Patient();
        Patient expectedOld = new Patient();

        when(patientService.getPatientById(anyLong())).thenReturn(expectedOld);
        when(patientService.updatePatient(expectedOld, expectedNew)).thenReturn(expectedOld);
        ResponseEntity actual = patientController.updatePatient((long)4, expectedNew);
        verify(patientService).updatePatient(expectedOld, expectedNew);
        verify(patientService).getPatientById((long)4);
        assertEquals(expectedOld, actual.getBody());
    }

    @Test
    public void update_return404StatusWhenPatientNotFound(){
        Patient expectedNew = new Patient();
        when(patientService.getPatientById(anyLong())).thenThrow(EntityDoesNotExistException.class);
        ResponseEntity actual = patientController.updatePatient((long)4, expectedNew);
        verify(patientService).getPatientById((long)4);

        assertEquals(404, actual.getStatusCodeValue());
    }


}