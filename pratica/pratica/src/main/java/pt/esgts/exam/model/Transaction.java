package pt.esgts.exam.model;

/**
 * Represents a transaction
 *
 * @author Bruno Jesus
 * @version 1.0
 * @see Account
 */
public class Transaction {
    private final Account origin;
    private final Account destination;
    private final double amount;

    public Transaction(Account origin, Account destination, double amount) {
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
    }

    /**
     * The account that did the transaction
     *
     * @return the origin {@link Account}
     */
    public Account getOrigin() {
        return origin;
    }


    /**
     * The account that received the transaction
     *
     * @return the destination {@link Account}
     */
    public Account getDestination() {
        return destination;
    }

    /**
     * The amount being transacted from the {@link #getOrigin() origin}
     * to the {@link #getDestination()} account.
     *
     * @return the amount being transacted
     */
    public double getAmount() {
        return amount;
    }
}
