package ma.ac.ensa.ebankingapi.exception;

public class ConvertDtoToEntityException extends RuntimeException {
    public ConvertDtoToEntityException() {
        super();
    }

    public ConvertDtoToEntityException(String message) {
        super(message);
    }

    public ConvertDtoToEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertDtoToEntityException(Throwable cause) {
        super(cause);
    }

    protected ConvertDtoToEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
