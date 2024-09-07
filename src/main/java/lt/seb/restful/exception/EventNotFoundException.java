package lt.seb.restful.exception;

import lombok.Data;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException() {
        super("Event not found");
    }
}
