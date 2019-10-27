package daniella.iths.cleancodelabb2daniellanoren.Models;

import daniella.iths.cleancodelabb2daniellanoren.Exceptions.InvalidTimeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoctorsAppointmentTest {

    private DoctorsAppointment doctorsAppointment;

    @Before
    public void init(){
        doctorsAppointment = new DoctorsAppointment();
    }
    @Test(expected = InvalidTimeException.class)
    public void setTime_throwExceptionWhenInvalidFormatLetters(){
        doctorsAppointment.setTime("sdbasdj");
    }

    @Test(expected = InvalidTimeException.class)
    public void setTime_throwExceptionWhenInvalidFormatNumbers(){
        doctorsAppointment.setTime("1945034953450");
    }

    @Test(expected = InvalidTimeException.class)
    public void setTime_throwExceptionWhenInvalidTime(){
        doctorsAppointment.setTime("12:65");
    }

    @Test(expected = InvalidTimeException.class)
    public void setDate_throwExceptionWhenInvalidDateFormat(){
        doctorsAppointment.setDate("2038539458345");
    }

    @Test(expected = InvalidTimeException.class)
    public void setDate_throwExceptionWhenInvalidDateFormatWithoutLines(){
        doctorsAppointment.setDate("20190312");
    }

    @Test(expected = InvalidTimeException.class)
    public void setDate_throwExceptionWhenCorrectFormatWrongDate(){
        doctorsAppointment.setDate("2017-04-02");
    }

    @Test
    public void setDate_normal(){
        doctorsAppointment.setDate("2019-12-12");
        assertEquals("2019-12-12", doctorsAppointment.getDate());
    }

    @Test
    public void setTime_normal(){
        doctorsAppointment.setTime("20:05");
        assertEquals("20:05", doctorsAppointment.getTime());
    }

}
