package acardenas.com.handler;

import acardenas.com.exception.ValidationException;
import acardenas.com.model.ExceptionResponse;
import acardenas.com.model.ObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

@ControllerAdvice
public class RestExceptionHandler {
    //exceptions a tratar
    @ExceptionHandler({ Throwable.class})
    public ResponseEntity<?> exceptionCatcher(HttpServletRequest request, Exception ex) {
        //armado de respuesta de una exception
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StackTraceElement traceElement = Stream.of(ex.getStackTrace()).findFirst().get();

        if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
        }

        ObjectResponse<Object> body = ObjectResponse.builder()
                .path(request.getRequestURI())
                .apiError(
                        ExceptionResponse.builder()
                                .typeClass(ex.getClass().getName())
                                .message(ex.getMessage())
                                .errorTrack(traceElement).build())
                .status(status.value()).build();

        return new ResponseEntity<>(body, status);
    }

}
