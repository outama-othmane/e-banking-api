package ma.ac.ensa.ebankingapi.handlers;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ma.ac.ensa.ebankingapi.dtos.ErrorDto;
import ma.ac.ensa.ebankingapi.exception.*;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException() {

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
    public ResponseEntity<?> handleInvalidJwtTokenException() {

        //
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("Unauthenticated.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException() {

        //
        final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("Unauthorized.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<?> handleInvalidFieldException(InvalidFieldException exception) {

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
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message("Not Found.")
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpStatusCode(httpStatus.value())
                .message(String.format("The given [/%s] parameter is invalid.", ex.getName()))
                .build();

        return new ResponseEntity<>(errorDto, httpStatus);
    }
}
