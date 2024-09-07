package lt.seb.restful.exception;

public record EventErrorResponse(
        String message, int code
) {

}
