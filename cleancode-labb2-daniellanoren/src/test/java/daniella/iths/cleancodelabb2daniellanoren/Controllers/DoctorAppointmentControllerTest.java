package daniella.iths.cleancodelabb2daniellanoren.Controllers;

import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidEntityException;
import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
import daniella.iths.cleancodelabb2daniellanoren.Services.DoctorsAppointmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DoctorAppointmentControllerTest {

    @InjectMocks
    private DoctorsAppointmentController doctorsAppointmentController = new DoctorsAppointmentController();

    @Mock
    private DoctorsAppointmentService doctorsAppointmentService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll(){
        List<DoctorsAppointment> visits = new ArrayList<>(Arrays.asList(new DoctorsAppointment(),
                new DoctorsAppointment(),
                new DoctorsAppointment()));

        when(doctorsAppointmentService.getAllDoctorsAppointments()).thenReturn(visits);
        List<DoctorsAppointment> actual = (List<DoctorsAppointment>)doctorsAppointmentController.getAll().getBody();
        verify(doctorsAppointmentService).getAllDoctorsAppointments();

        for(int i = 0; i < visits.size(); i++){
            assertEquals(visits.get(0), actual.get(0));
        }
    }

    @Test
    public void getAll_empty(){
        List<DoctorsAppointment> doctorAppointments = (List<DoctorsAppointment>)doctorsAppointmentController.getAll().getBody();
        verify(doctorsAppointmentService).getAllDoctorsAppointments();
        assertEquals(0, doctorAppointments.size());
    }

    @Test
    public void getAppointment_normal(){
        DoctorsAppointment expected = new DoctorsAppointment();
        when(doctorsAppointmentService.getDoctorAppointmentById((long)5)).thenReturn(expected);
        DoctorsAppointment actual = (DoctorsAppointment) doctorsAppointmentController.getAppointment((long)5).getBody();
        verify(doctorsAppointmentService).getDoctorAppointmentById((long)5);
        assertEquals(expected, actual);
    }

    @Test
    public void getAppointment_return404StatusWhenExceptionThrown(){
        when(doctorsAppointmentService.getDoctorAppointmentById(anyLong())).thenThrow(EntityDoesNotExistException.class);
        ResponseEntity actual = doctorsAppointmentController.getAppointment((long)4);
        verify(doctorsAppointmentService).getDoctorAppointmentById((long)4);
        assertEquals(404, actual.getStatusCodeValue());
    }

    @Test
    public void createAppointment_returnAppointmentWhenCreated(){
        DoctorsAppointment appointment = new DoctorsAppointment();
        when(doctorsAppointmentService.saveNewAppointment(appointment)).thenReturn(appointment);
        DoctorsAppointment actual = (DoctorsAppointment) doctorsAppointmentController.createAppointment(appointment).getBody();
        verify(doctorsAppointmentService).saveNewAppointment(appointment);
        assertEquals(appointment, actual);
    }

    @Test
    public void createAppointment_return500StatusWhenExceptionThrown(){
        DoctorsAppointment appointment = new DoctorsAppointment();
        when(doctorsAppointmentService.saveNewAppointment(appointment)).thenThrow(InvalidEntityException.class);
        ResponseEntity actual = doctorsAppointmentController.createAppointment(appointment);
        verify(doctorsAppointmentService).saveNewAppointment(appointment);
        assertEquals(500, actual.getStatusCodeValue());
    }

    @Test
    public void delete_return200StatusWhenNormal(){
        DoctorsAppointment expected = new DoctorsAppointment();
        when(doctorsAppointmentService.getDoctorAppointmentById(anyLong())).thenReturn(expected);
        doAnswer((i) -> { assertEquals(expected, i.getArgument(0)); return null;} ).when(doctorsAppointmentService).deleteAppointment(expected);
        ResponseEntity actual = doctorsAppointmentController.deleteAppointment((long) 4);
        verify(doctorsAppointmentService).getDoctorAppointmentById((long) 4);
        verify(doctorsAppointmentService).deleteAppointment(expected);
        assertEquals(200, actual.getStatusCodeValue());
    }

    @Test
    public void delete_return404StatusWhenAppointmentDoesNotExist(){
        when(doctorsAppointmentService.getDoctorAppointmentById(anyLong())).thenThrow(EntityNotFoundException.class);
        ResponseEntity actual = doctorsAppointmentController.deleteAppointment((long) 4);
        verify(doctorsAppointmentService).getDoctorAppointmentById((long) 4);
        assertEquals(404, actual.getStatusCodeValue());
    }

    @Test
    public void update_returnAppointmentWhenNormal(){
        DoctorsAppointment doctorsAppointmentNew = new DoctorsAppointment();
        DoctorsAppointment doctorsAppointmentOld = new DoctorsAppointment();
        when(doctorsAppointmentService.getDoctorAppointmentById(anyLong())).thenReturn(doctorsAppointmentOld);
        when(doctorsAppointmentService.updateDoctorsAppointment(doctorsAppointmentNew, doctorsAppointmentOld)).thenReturn(doctorsAppointmentOld);
        ResponseEntity actual = doctorsAppointmentController.updateAppointment((long)4, doctorsAppointmentOld);
        verify(doctorsAppointmentService).getDoctorAppointmentById((long)4);
        verify(doctorsAppointmentService).updateDoctorsAppointment(doctorsAppointmentNew, doctorsAppointmentOld);
        assertEquals(doctorsAppointmentOld, actual.getBody());
    }

    @Test
    public void update_return404StatusWhenAppointmentNotFound(){
        DoctorsAppointment expected = new DoctorsAppointment();
        expected.setId((long)4);
        when(doctorsAppointmentService.getDoctorAppointmentById(anyLong())).thenThrow(EntityNotFoundException.class);
        ResponseEntity actual = doctorsAppointmentController.updateAppointment((long)4, expected);
        verify(doctorsAppointmentService).getDoctorAppointmentById((long) 4);
        assertEquals(404, actual.getStatusCodeValue());
    }



}