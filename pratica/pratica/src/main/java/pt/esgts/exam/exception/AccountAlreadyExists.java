package pt.esgts.exam.exception;

public class AccountAlreadyExists extends Exception {
    public AccountAlreadyExists() {
    }

    public AccountAlreadyExists(String message) {
        super(message);
    }
}
