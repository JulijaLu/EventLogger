package lt.seb.restful.api.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventException {

    private String message;
    private HttpStatus httpStatus;
    private List<String> errors;
    private ZonedDateTime timestamp;

    public EventException(String message, HttpStatus httpStatus, List<String> errors, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.errors = new ArrayList<>();
        this.timestamp = timestamp;
    }

    public EventException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

}
