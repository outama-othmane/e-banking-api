package ma.ac.ensa.ebankingapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InvalidFieldException extends RuntimeException {

    private String fieldName;

    private String error;

    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String fieldName, String error) {
        super();
        this.fieldName = fieldName;
        this.error = error;
    }

    public InvalidFieldException(String fieldName, String error, Throwable cause) {
        super(cause);
        this.fieldName = fieldName;
        this.error = error;
    }

    public InvalidFieldException(Throwable cause) {
        super(cause);
    }
}
