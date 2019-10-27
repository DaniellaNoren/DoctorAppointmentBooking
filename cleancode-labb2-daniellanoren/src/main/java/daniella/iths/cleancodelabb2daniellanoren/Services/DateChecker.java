package daniella.iths.cleancodelabb2daniellanoren.Services;

import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class DateChecker {

    private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);


    public static boolean checkIfValidYearBorn(int year){
        return year < CURRENT_YEAR && year >= 1903;
    }

    public static boolean checkIfValidYearForDateBooking(int year){
        return year >= CURRENT_YEAR;
    }

    public static boolean checkIfValidMonth(int month){
        return month >= 1 && month <= 12;
    }

    public static boolean checkIfValidDay(int day, int month, int year){
        int maxDays = 31;

        if(month == 2)
            maxDays = isLeapYear(year) ? 29 : 28;
        else if(month % 2 == 0)
            maxDays = 30;

        return day >= 1 && day <= maxDays;
    }

    public static boolean isLeapYear(int year){
        return year > 0 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
}
