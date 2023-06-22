package pt.esgts.exam.exception;

public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
    }

    public InsufficientFunds(String message) {
        super(message);
    }
}
