package lt.seb.restful.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@ControllerAdvice
public class EventValidationHandler extends AbstractHandlerExceptionResolver {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {EventRequestException.class})
    public ResponseEntity<Object> handleEventRequestException(EventRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        EventException eventException = new EventException(e.getMessage());
        return new ResponseEntity<>(eventException, badRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EventRequestException.class})
    public ResponseEntity<Object> notFoundException(EventRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        EventException eventException = new EventException(e.getMessage());
        return new ResponseEntity<>(eventException, badRequest);
    }

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex)
    {
        return null;
    }
}
