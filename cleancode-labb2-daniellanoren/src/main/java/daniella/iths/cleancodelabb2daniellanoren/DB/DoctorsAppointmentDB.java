package daniella.iths.cleancodelabb2daniellanoren.DB;

import daniella.iths.cleancodelabb2daniellanoren.Models.DoctorsAppointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorsAppointmentDB extends CrudRepository<DoctorsAppointment, Long> {

    public List<DoctorsAppointment> findAll();

}
