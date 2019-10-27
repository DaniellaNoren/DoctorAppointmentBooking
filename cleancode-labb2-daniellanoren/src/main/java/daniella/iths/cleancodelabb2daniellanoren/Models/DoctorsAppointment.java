package daniella.iths.cleancodelabb2daniellanoren.Models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidTimeException;
import daniella.iths.cleancodelabb2daniellanoren.Services.DateChecker;
import daniella.iths.cleancodelabb2daniellanoren.Services.TimeChecker;

import javax.persistence.*;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

@Entity
public class DoctorsAppointment {

    private String date;
    private String time;
    private String appointmentDescription;
    private boolean canceled;
    private String doctorName;

    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;

    public DoctorsAppointment(){

    }

    public DoctorsAppointment(String date, String time, String appointmentDescription, String doctorName, Patient patient) {
        this.date = date;
        this.time = time;
        this.appointmentDescription = appointmentDescription;
        this.doctorName = doctorName;
        this.patient = patient;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if(isDateValid(date)){
                this.date = date;
            }else
                throw new InvalidTimeException("Date not valid");
    }

    public static boolean isDateValid(String date) {
        if (date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            int day = Integer.parseInt(date.substring(8, 10));
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            return DateChecker.checkIfValidDay(day, year, month) &&
                   DateChecker.checkIfValidMonth(month) &&
                   DateChecker.checkIfValidYearForDateBooking(year);
         }else
            return false;

    }

    public static boolean isTimeValid(String time){
        if(time.matches("[0-2][0-5]:[0-5][0-9]")){
            int hour = Integer.parseInt(time.substring(0, 2));
            int minutes = Integer.parseInt(time.substring(3, 5));
            return TimeChecker.checkIfValidTime(hour, minutes);
        }  else
            return false;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if(isTimeValid(time)){
          this.time = time;
        }  else
           throw new InvalidTimeException("Time not properly formatted 00:00 - 23:59");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorsAppointment that = (DoctorsAppointment) o;
        return canceled == that.canceled &&
                id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(appointmentDescription, that.appointmentDescription) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(doctorName, that.doctorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, appointmentDescription, canceled, patient, doctorName, id);
    }
}
