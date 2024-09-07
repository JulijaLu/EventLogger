package lt.seb.restful.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.lang.reflect.Type;

@ControllerAdvice
public class EventValidationHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(value = {EventRequestException.class,
            TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<EventErrorResponse> handleEventRequestException(RuntimeException e) {
        EventErrorResponse eventErrorResponse = new EventErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(eventErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<EventErrorResponse> notFoundException(EventNotFoundException e) {
        EventErrorResponse eventErrorResponse = new EventErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(eventErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> internalServerException(RuntimeException e) {
        EventErrorResponse eventErrorResponse = new EventErrorResponse(
                e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(eventErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
