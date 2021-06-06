package ma.ac.ensa.ebankingapi.handlers;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ma.ac.ensa.ebankingapi.dtos.ErrorDto;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.exception.InvalidCredentialsException;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.exception.InvalidJwtTokenException;
import ma.ac.ensa.ebankingapi.exception.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Set;

// @RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: Unauthenticated exception handler

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

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception,
                                                              WebRequest webRequest) {

        //
        final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("Unauthorized.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<?> handleUnauthorizedException(InvalidFieldException exception,
                                                              WebRequest webRequest) {

        Map<String, Set<String>> errors = Maps.newHashMap();
        errors.put(exception.getFieldName(), Sets.newHashSet(exception.getError()));

        final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .errors(errors)
                .message("The given data was invalid.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
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

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NotNull MissingPathVariableException ex,
                                                               @NotNull HttpHeaders headers,
                                                               @NotNull HttpStatus status,
                                                               @NotNull WebRequest request) {
        // TODO: Update this method
        return super.handleMissingPathVariable(ex, headers, status, request);
    }
}
