package ma.ac.ensa.ebankingapi.handlers;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ma.ac.ensa.ebankingapi.dtos.ErrorDto;
import ma.ac.ensa.ebankingapi.exception.InvalidCredentialsException;
import ma.ac.ensa.ebankingapi.exception.InvalidJwtTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException exception,
                                                              WebRequest webRequest) {

        Set<String> usernameSet = Sets.newHashSet("Wrong username or password.");

        Map<String, Set<String>> errors = Maps.newHashMap();
        errors.put("username", usernameSet);

        final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("The given data was invalid.")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<?> handleInvalidJwtTokenException(InvalidJwtTokenException exception,
                                                              WebRequest webRequest) {

        //
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("Unauthenticated.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
       Map<String, Set<String>> errors = Maps.newHashMap();

        for(FieldError error : exception.getFieldErrors()) {
            String message = String.format("%s %s", error.getField(), error.getDefaultMessage());

            if (errors.containsKey(error.getField())) {
                Set<String> messages = errors.get(error.getField());
                messages.add(message);

                errors.put(error.getField(), messages);
            } else {
                errors.put(error.getField(),
                        Sets.newHashSet(message));
            }
        }

        final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("The given data was invalid.")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }


}
