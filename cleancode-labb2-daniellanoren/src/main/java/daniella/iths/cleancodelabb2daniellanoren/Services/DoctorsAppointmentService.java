package daniella.iths.cleancodelabb2daniellanoren.Services;

import daniella.iths.cleancodelabb2daniellanoren.DB.DoctorsAppointmentDB;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorsAppointmentService {

    @Autowired
    private DoctorsAppointmentDB doctorsAppointmentResource;

    @Autowired
    private PatientService patientService;

    public DoctorsAppointment getDoctorAppointmentById(long id){
        Optional<DoctorsAppointment> doctorsAppointment = doctorsAppointmentResource.findById(id);
        if(doctorsAppointment.isPresent())
            return doctorsAppointment.get();
        else
            throw new EntityDoesNotExistException("Appointment with that ID does not exist");
    }

    public List<DoctorsAppointment> getAllDoctorsAppointments(){
        return doctorsAppointmentResource.findAll();
    }

    public List<DoctorsAppointment> getAllDoctorsAppointmentsWithPatient(long patientId){
        return doctorsAppointmentResource.findAll().stream().filter(app -> app.getPatient().getId() == patientId).collect(Collectors.toList());
    }

    public List<DoctorsAppointment> getAllNotCanceledAppointments(){
        return doctorsAppointmentResource.findAll().stream().filter(app -> !app.isCanceled()).collect(Collectors.toList());
    }

    public DoctorsAppointment saveNewAppointment(DoctorsAppointment doctorsAppointment){
        Patient patient;
        try{
            if(doctorsAppointment.getPatient().getId() > 0){
                patient = patientService.getPatientById(doctorsAppointment.getPatient().getId());
                doctorsAppointment.setPatient(patient);
                patient.addAppointment(doctorsAppointment);
            }
        }catch(EntityDoesNotExistException e){
            throw new EntityDoesNotExistException("Patient with that id does not exist");
        }

        doctorsAppointmentResource.save(doctorsAppointment);
        return doctorsAppointment;
    }

    public void deleteAppointment(DoctorsAppointment doctorsAppointment){
        doctorsAppointment.getPatient().deleteAppointment(doctorsAppointment);
        doctorsAppointmentResource.delete(doctorsAppointment);
    }

    public DoctorsAppointment updateDoctorsAppointment(DoctorsAppointment newDoctorsAppointment, DoctorsAppointment oldDoctorsAppointment){

        this.updateDoctorsAppointmentFields(newDoctorsAppointment, oldDoctorsAppointment);
        doctorsAppointmentResource.save(oldDoctorsAppointment);
        return oldDoctorsAppointment;
    }

    public void updateDoctorsAppointmentFields(DoctorsAppointment newDoctorsAppointment, DoctorsAppointment oldDoctorsAppointment){

        if(newDoctorsAppointment.isCanceled())
            oldDoctorsAppointment.setCanceled(true);
        if(newDoctorsAppointment.getTime() != null)
            oldDoctorsAppointment.setTime(newDoctorsAppointment.getTime());
        if(newDoctorsAppointment.getDate() != null)
            oldDoctorsAppointment.setDate(newDoctorsAppointment.getDate());
        if(newDoctorsAppointment.getDoctorName() != null)
            oldDoctorsAppointment.setDoctorName(newDoctorsAppointment.getDoctorName());
        if(newDoctorsAppointment.getAppointmentDescription() != null)
            oldDoctorsAppointment.setAppointmentDescription(newDoctorsAppointment.getAppointmentDescription());
    }


}
