package lt.seb.restful.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
@Slf4j
public class EventValidationHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(value = {TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<EventErrorResponse> handleEventRequestException(TypeMismatchException e) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<EventErrorResponse> notFoundException(EventNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<EventErrorResponse> internalServerException(RuntimeException e) {
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T extends RuntimeException> ResponseEntity<EventErrorResponse> buildErrorResponse(T e, HttpStatus status) {
        EventErrorResponse eventErrorResponse = new EventErrorResponse(e.getMessage(), status.value());
        return new ResponseEntity<>(eventErrorResponse, status);
    }
}