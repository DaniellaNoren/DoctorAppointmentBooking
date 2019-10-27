package daniella.iths.cleancodelabb2daniellanoren.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Patient {

    @Column(unique = true)
    private String email;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnore
    private List<DoctorsAppointment> appointments;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public Patient(){

    }

    public Patient(String email, String name, List<DoctorsAppointment> appointments){
        this.email = email;
        this.name = name;
        this.appointments = appointments;
    }

    public List<DoctorsAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<DoctorsAppointment> appointments) {
        this.appointments = appointments;
    }

    public void deleteAppointment(DoctorsAppointment doctorsAppointment){appointments.remove(doctorsAppointment);}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addAppointments(List<DoctorsAppointment> doctorsAppointments){
        appointments.addAll(doctorsAppointments);
    }

    public void addAppointment(DoctorsAppointment doctorsAppointment){
        appointments.add(doctorsAppointment);
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", appointments=" + appointments +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                Objects.equals(email, patient.email) &&
                Objects.equals(name, patient.name) &&
                Objects.equals(appointments, patient.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, appointments, id);
    }
}
