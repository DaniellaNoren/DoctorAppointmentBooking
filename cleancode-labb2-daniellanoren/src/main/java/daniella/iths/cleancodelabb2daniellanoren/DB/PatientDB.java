package daniella.iths.cleancodelabb2daniellanoren.DB;

import daniella.iths.cleancodelabb2daniellanoren.Models.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientDB extends CrudRepository<Patient, Long> {
    public List<Patient> findAll();
}
