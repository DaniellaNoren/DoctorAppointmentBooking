package daniella.iths.cleancodelabb2daniellanoren.Services;

import daniella.iths.cleancodelabb2daniellanoren.DB.DoctorsAppointmentDB;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
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
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class DoctorsAppointmentServiceTest {

    @InjectMocks
    private DoctorsAppointmentService doctorsAppointmentService = new DoctorsAppointmentService();

    @Mock
    private DoctorsAppointmentDB doctorsAppointmentDB;

    @Mock
    private PatientService patientService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void getDoctorsAppointmentById_throwExceptionIfNotFound(){
        when(doctorsAppointmentDB.findById(anyLong())).thenReturn(Optional.empty());
        doctorsAppointmentService.getDoctorAppointmentById((long)4);
        verify(doctorsAppointmentDB.findById((long)4));
    }

    @Test
    public void getDoctorsAppointmentById_normal(){
        DoctorsAppointment doctorsAppointment = new DoctorsAppointment();
        when(doctorsAppointmentDB.findById(anyLong())).thenReturn(Optional.of(doctorsAppointment));
        assertEquals(doctorsAppointment, doctorsAppointmentService.getDoctorAppointmentById((long)4));
        verify(doctorsAppointmentDB).findById((long)4);
    }

    @Test
    public void getAllDoctorsAppointments_normal(){
        List<DoctorsAppointment> expected = new ArrayList<>(Arrays.asList(new DoctorsAppointment(),new DoctorsAppointment()));
        when(doctorsAppointmentDB.findAll()).thenReturn(expected);
        List<DoctorsAppointment> actual = doctorsAppointmentService.getAllDoctorsAppointments();
        verify(doctorsAppointmentDB).findAll();
        for(int i = 0; i < actual.size(); i++){
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void getAllAppointmentsByPatientIdTest(){
        Patient patient = new Patient();
        patient.setId((long)5);
        Patient notChosenPatient = new Patient();
        patient.setId((long)2);

        List<DoctorsAppointment> expected = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            DoctorsAppointment appointment = new DoctorsAppointment();
            appointment.setPatient(patient);
            expected.add(appointment);
        }
        DoctorsAppointment appointment = new DoctorsAppointment();
        appointment.setPatient(notChosenPatient);

        when(doctorsAppointmentDB.findAll()).thenReturn(expected);
        List<DoctorsAppointment> actual = doctorsAppointmentService.getAllDoctorsAppointmentsWithPatient((long)5);
        for(DoctorsAppointment d : actual){
            assertEquals(patient, d.getPatient());
        }
    }

    @Test
    public void getAllNotCanceledAppointments(){
        List<DoctorsAppointment> expected = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            DoctorsAppointment appointment = new DoctorsAppointment();
            appointment.setCanceled(true);
            expected.add(appointment);
        }
        DoctorsAppointment appointment = new DoctorsAppointment();
        expected.add(appointment);

        when(doctorsAppointmentDB.findAll()).thenReturn(expected);
        List<DoctorsAppointment> actual = doctorsAppointmentService.getAllNotCanceledAppointments();
        for(DoctorsAppointment d : actual){
            assertFalse(d.isCanceled());
        }
    }


    @Test
    public void updateDoctorsAppointmentFields_updateAllFields(){
        DoctorsAppointment oldDoctorsAppointment = new DoctorsAppointment();
        DoctorsAppointment newDoctorsAppointmentFields = new DoctorsAppointment();

        newDoctorsAppointmentFields.setDoctorName("Dr. Boris");
        newDoctorsAppointmentFields.setDate("2020-03-04");
        newDoctorsAppointmentFields.setTime("14:30");
        newDoctorsAppointmentFields.setAppointmentDescription("An appointment");

        doctorsAppointmentService.updateDoctorsAppointmentFields(newDoctorsAppointmentFields, oldDoctorsAppointment);
        assertEquals("Dr. Boris", oldDoctorsAppointment.getDoctorName());
        assertEquals("2020-03-04", oldDoctorsAppointment.getDate());
        assertEquals("14:30", oldDoctorsAppointment.getTime());
        assertEquals("An appointment", oldDoctorsAppointment.getAppointmentDescription());
    }

    @Test
    public void updateDoctorsAppointmentFields_updateSomeFields(){
        DoctorsAppointment oldDoctorsAppointment = new DoctorsAppointment();
        DoctorsAppointment newDoctorsAppointmentFields = new DoctorsAppointment();

        oldDoctorsAppointment.setDate("2020-03-10");
        newDoctorsAppointmentFields.setDoctorName("Dr. Boris");

        doctorsAppointmentService.updateDoctorsAppointmentFields(newDoctorsAppointmentFields, oldDoctorsAppointment);
        assertEquals("Dr. Boris", oldDoctorsAppointment.getDoctorName());
        assertEquals("2020-03-10", oldDoctorsAppointment.getDate());

    }

   @Test
    public void saveNewAppointment_normal(){
        DoctorsAppointment doctorsAppointment = new DoctorsAppointment();
        doctorsAppointment.setId((long)4);
        Patient patient = new Patient("Test", "Test", new ArrayList<>());
        patient.setId((long)2);
        doctorsAppointment.setPatient(patient);

        when(patientService.getPatientById(anyLong())).thenReturn(patient);
        when(doctorsAppointmentDB.save(doctorsAppointment)).thenReturn(doctorsAppointment);

        DoctorsAppointment actual = doctorsAppointmentService.saveNewAppointment(doctorsAppointment);
        verify(doctorsAppointmentDB).save(doctorsAppointment);
        assertEquals(doctorsAppointment, actual);
   }

    @Test(expected = EntityDoesNotExistException.class)
    public void saveNewAppointment_throwExceptionWhenPatientDoesNotExist(){
        DoctorsAppointment doctorsAppointment = new DoctorsAppointment();
        doctorsAppointment.setId((long)4);
        Patient patient = new Patient();
        patient.setId((long)1);
        doctorsAppointment.setPatient(patient);

        when(patientService.getPatientById(anyLong())).thenThrow(EntityDoesNotExistException.class);

        doctorsAppointmentService.saveNewAppointment(doctorsAppointment);
        verify(doctorsAppointmentDB).save(doctorsAppointment);

    }

}
