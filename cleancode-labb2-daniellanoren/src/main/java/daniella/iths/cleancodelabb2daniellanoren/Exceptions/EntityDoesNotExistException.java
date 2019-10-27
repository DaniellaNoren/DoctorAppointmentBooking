package daniella.iths.cleancodelabb2daniellanoren.Exceptions;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(String msg){
        super(msg);
    }
}
