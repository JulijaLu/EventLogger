package lt.seb.restful.exception;


public class EventRequestException extends RuntimeException {

    public EventRequestException() {
    }

    public EventRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
