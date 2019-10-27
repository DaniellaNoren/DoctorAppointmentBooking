package daniella.iths.cleancodelabb2daniellanoren.Services;

import org.springframework.stereotype.Service;

@Service
public class TimeChecker {

    public static boolean checkIfValidTime(int hour, int minutes){
        return hour >= 0 && hour <= 23 && minutes >= 0 && minutes <= 59;
    }
}
