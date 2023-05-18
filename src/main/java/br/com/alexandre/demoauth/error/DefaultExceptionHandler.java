package br.com.alexandre.demoauth.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(final Throwable throwable, final HttpServletRequest httpRequest) throws Exception {
        RestError re = new RestError(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
