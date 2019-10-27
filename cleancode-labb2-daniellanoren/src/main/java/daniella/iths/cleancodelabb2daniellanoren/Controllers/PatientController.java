package daniella.iths.cleancodelabb2daniellanoren.Controllers;


import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidEntityException;
import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import daniella.iths.cleancodelabb2daniellanoren.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllPatients(){
        return ResponseEntity.status(200).body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getPatient(@PathVariable("id") long id){
        Patient patient;
        try{
            patient = patientService.getPatientById(id);
            return ResponseEntity.status(200).body(patient);
        }catch(EntityDoesNotExistException e){
            return ResponseEntity.status(404).body("Patient with that Id does not exist");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updatePatient(@PathVariable("id")long id, @RequestBody Patient newPatient){
        Patient patient;
        try{
            patient = patientService.getPatientById(id);
            patientService.updatePatient(patient, newPatient);
            return ResponseEntity.status(201).body(patient);
        }catch(EntityDoesNotExistException e){
            return ResponseEntity.status(404).body("Patient with that Id does not exist");
        }catch(InvalidEntityException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient fields not valid");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable("id")long id){
        Patient patient;
        try{
            patient = patientService.getPatientById(id);
            patientService.deletePatient(patient);
            return ResponseEntity.status(200).body("Patient with id "+id+" successfully deleted");
        }catch(EntityDoesNotExistException e){
            return ResponseEntity.status(404).body("Patient with that Id does not exist");
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createNewPatient(@RequestBody Patient newPatient){
        try{
            Patient patient = patientService.saveNewPatient(newPatient);
            return ResponseEntity.status(201).body(patient);
        }catch(InvalidEntityException e){
            return ResponseEntity.status(500).body("Patient fields not valid");
        }
    }

}
