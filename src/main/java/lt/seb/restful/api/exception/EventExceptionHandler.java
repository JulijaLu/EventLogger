package lt.seb.restful.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class EventExceptionHandler {

    @ExceptionHandler(value = {EventRequestException.class})
    public ResponseEntity<Object> handleEventRequestException(EventRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        EventException eventException = new EventException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(eventException, badRequest);
    }


}
