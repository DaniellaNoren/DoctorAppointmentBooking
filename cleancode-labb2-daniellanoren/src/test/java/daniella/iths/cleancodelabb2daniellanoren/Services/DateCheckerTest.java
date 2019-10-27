package daniella.iths.cleancodelabb2daniellanoren.Services;


import org.junit.Test;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateCheckerTest {


    /** Valid Year interval */

    @Test
    public void validYearDateBooking_returnTrueWhenCurrentYear(){
        assertTrue(DateChecker.checkIfValidYearForDateBooking(Calendar.getInstance().get(Calendar.YEAR)));
    }

    @Test
    public void validYearDateBooking_returnTrueWhenFutureYear(){
        assertTrue(DateChecker.checkIfValidYearForDateBooking(Calendar.getInstance().get(Calendar.YEAR)+5));
    }

    @Test
    public void validYearDateBooking_returnFalseWhenPastYear(){
        assertFalse(DateChecker.checkIfValidYearForDateBooking(2018));
        assertFalse(DateChecker.checkIfValidYearForDateBooking(2005));
    }

    /** Valid Year Born */

    @Test
    public void validYearBorn_returnFalseWhenFutureYear(){
        assertFalse(DateChecker.checkIfValidYearBorn(2066));
    }

    @Test
    public void validYearBorn_returnFalseWhenTooOld(){
        assertFalse(DateChecker.checkIfValidYearBorn(1900));
    }

    @Test
    public void validYearBorn_returnFalseWhenNotAYear(){
        assertFalse(DateChecker.checkIfValidYearBorn(-4));
    }

    @Test
    public void validYearBorn_returnFalseWhenCurrentYear(){
        assertFalse(DateChecker.checkIfValidYearBorn(2019));
    }

    /** Valid month */

    @Test
    public void validMonth_returnFalseWhenImpossibleMonth(){
        assertFalse(DateChecker.checkIfValidMonth(13));
    }

    @Test
    public void validMonth_returnFalseWhenNegative(){
        assertFalse(DateChecker.checkIfValidMonth(-2));
    }

    @Test
    public void validMonth_returnTrueWhenNormalMonth(){
        assertTrue(DateChecker.checkIfValidMonth(1));
        assertTrue(DateChecker.checkIfValidMonth(2));
    }

    /** Valid day */

    @Test
    public void validDay_returnFalseWhenImpossibleDay(){
        assertFalse(DateChecker.checkIfValidDay(32, 3, 2019));
    }

    @Test
    public void validDay_returnFalseWhenInvalidDayWhenEvenMonth(){
        assertFalse(DateChecker.checkIfValidDay(31, 4, 2019));
    }

    @Test
    public void validDay_returnFalseWhenInvalidDayWhenFebruary(){
        assertFalse(DateChecker.checkIfValidDay(30, 2, 2019));
    }

    @Test
    public void validDay_returnFalseWhenNotLeapYear(){
        assertFalse(DateChecker.checkIfValidDay(29, 2, 2007));
    }

    @Test
    public void validDay_returnTrueWhenLeapYear(){
        assertTrue(DateChecker.checkIfValidDay(29, 2, 2008));
    }

    /** Leap year */

    @Test
    public void leapYear_returnTrueWhenActualLeapYear(){
        assertTrue(DateChecker.isLeapYear(2008));
        assertTrue(DateChecker.isLeapYear(1876));
    }

    @Test
    public void leapYear_returnFalseWhenNotLeapYear(){
        assertFalse(DateChecker.isLeapYear(2007));
        assertFalse(DateChecker.isLeapYear(853));
    }

    @Test
    public void leapYear_returnFalseWhenNotYear(){
        assertFalse(DateChecker.isLeapYear(-2));
    }



}
