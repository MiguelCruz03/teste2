package pt.esgts.exam.exception;


/**
 * Exception to be thrown if trying to access an {@link pt.esgts.exam.model.Account} that does not exist
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class AccountNotFound extends Exception {
    public AccountNotFound() {
    }

    public AccountNotFound(String message) {
        super(message);
    }

    public AccountNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
