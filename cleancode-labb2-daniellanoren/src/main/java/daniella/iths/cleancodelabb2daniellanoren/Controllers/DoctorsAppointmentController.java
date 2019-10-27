package daniella.iths.cleancodelabb2daniellanoren.Controllers;

import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
import daniella.iths.cleancodelabb2daniellanoren.Services.DoctorsAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/bookings")
public class DoctorsAppointmentController {

    @Autowired
    private DoctorsAppointmentService doctorsAppointmentService;

    @GetMapping
    @ResponseBody
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(doctorsAppointmentService.getAllDoctorsAppointments());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createAppointment(@RequestBody DoctorsAppointment doctorsAppointment){
       try{
           doctorsAppointmentService.saveNewAppointment(doctorsAppointment);
           return ResponseEntity.status(HttpStatus.CREATED).body(doctorsAppointment);
       }catch(Exception e){
           return ResponseEntity.status(500).body("Something went wrong");
       }



    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getAppointment(@PathVariable("id") long Id){
        DoctorsAppointment appointment;
        try{
            appointment = doctorsAppointmentService.getDoctorAppointmentById(Id);
            return ResponseEntity.status(HttpStatus.OK).body(appointment);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Visit with that ID can not be found");
        }
    }

    @GetMapping("/patients/{patient_id}")
    public ResponseEntity getAppointmentsByPatient(@PathVariable("patient_id") long id){
        List<DoctorsAppointment> doctorsAppointments = doctorsAppointmentService.getAllDoctorsAppointmentsWithPatient(id);
        return ResponseEntity.status(200).body(doctorsAppointments);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteAppointment(@PathVariable("id") long id){
        DoctorsAppointment appointment;
        try{
            appointment = doctorsAppointmentService.getDoctorAppointmentById(id);
            doctorsAppointmentService.deleteAppointment(appointment);
            return ResponseEntity.status(HttpStatus.OK).body("Visit with id: " + id + " deleted");
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Visit with that ID can not be found");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateAppointment(@PathVariable("id")long id, @RequestBody DoctorsAppointment newDoctorsAppointment){
        DoctorsAppointment doctorsAppointment;
        try{
            doctorsAppointment = doctorsAppointmentService.getDoctorAppointmentById(id);
            doctorsAppointmentService.updateDoctorsAppointment(newDoctorsAppointment, doctorsAppointment);
            return ResponseEntity.status(HttpStatus.OK).body(doctorsAppointment);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment with that Id can not be found");
        }
    }


}
