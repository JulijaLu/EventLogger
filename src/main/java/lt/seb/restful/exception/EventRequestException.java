package lt.seb.restful.exception;

public class EventRequestException extends RuntimeException {

    public EventRequestException() {
    }

    public EventRequestException(String message) {
        super(message);
    }

    public EventRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventRequestException(Throwable cause) {
        super(cause);
    }

    public EventRequestException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
