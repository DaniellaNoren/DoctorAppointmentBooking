package daniella.iths.cleancodelabb2daniellanoren.Services;

import daniella.iths.cleancodelabb2daniellanoren.DB.PatientDB;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.EntityDoesNotExistException;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidEntityException;
import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientDB patientResource;

    public Patient getPatientById(long id){
        Optional<Patient> patient = patientResource.findById(id);
        if(patient.isPresent())
            return patient.get();
        else
            throw new EntityDoesNotExistException("Patient with that ID does not exist");
    }

    public List<Patient> getAllPatients(){
        return patientResource.findAll();
    }

    public Patient saveNewPatient(Patient patient){
        try{
            return patientResource.save(patient);
        }catch(Exception e){
            throw new InvalidEntityException("Fields are not correct");
        }
    }

    public void deletePatient(Patient patient){
        patientResource.delete(patient);
    }

    public Patient updatePatient(Patient oldPatient, Patient newPatient){
        try {
            if (newPatient.getName() != null)
                oldPatient.setName(newPatient.getName());
            if (newPatient.getEmail() != null)
                oldPatient.setEmail(newPatient.getEmail());

            patientResource.save(oldPatient);
            return oldPatient;
        }catch(Exception e){
            throw new InvalidEntityException("Fields are not correct");
        }
    }

}
