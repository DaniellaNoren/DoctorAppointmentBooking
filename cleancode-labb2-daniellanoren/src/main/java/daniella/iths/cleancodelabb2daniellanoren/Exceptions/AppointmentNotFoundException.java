package daniella.iths.cleancodelabb2daniellanoren.Exceptions;

public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException(String msg){
        super(msg);
    }
}
