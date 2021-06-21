package ma.ac.ensa.ebankingapi.exception;

public class ConvertEntityToDtoException extends RuntimeException {
    public ConvertEntityToDtoException() {
        super();
    }

    public ConvertEntityToDtoException(String message) {
        super(message);
    }

    public ConvertEntityToDtoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertEntityToDtoException(Throwable cause) {
        super(cause);
    }

    protected ConvertEntityToDtoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
