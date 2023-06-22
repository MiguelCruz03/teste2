package pt.esgts.exam.service;

import pt.esgts.exam.exception.AccountAlreadyExists;
import pt.esgts.exam.exception.AccountNotFound;
import pt.esgts.exam.exception.InsufficientFunds;
import pt.esgts.exam.model.Account;
import pt.esgts.exam.model.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service for interacting with {@link Account Accounts} and make {@link Transaction Transactions}
 *
 * @author Bruno Jesus
 * @version 1.0
 * @see Account
 * @see Transaction
 */
public class AccountService {

    private final List<Transaction> transactions;
    private final Set<Account> accounts;
    private final AccountIdGenerator accountIdGenerator;

    public AccountService() {
        transactions = new ArrayList<>();
        accounts = new HashSet<>();
        accountIdGenerator = new RandomAccountIdGenerator();
    }

    public AccountService(
            List<Transaction> transactions,
            Set<Account> accounts,
            AccountIdGenerator accountIdGenerator) {
        this.transactions = transactions;
        this.accounts = accounts;
        this.accountIdGenerator = accountIdGenerator;
    }

    /**
     * Finds an {@link Account} in the system
     *
     * @param id the id of the {@link Account} to look for
     * @return the {@link Account} instance
     * @throws AccountNotFound if the {@link Account} does not exist
     */
    public Account findAccount(int id) throws AccountNotFound {
        return accounts.stream()
                .filter(acc -> acc.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AccountNotFound(
                        String.format("Account %d does not exist", id)
                ));
    }

    /**
     * Creates an {@link Account} in the system
     *
     * @param accountName the desired name for the account
     * @return the account's id
     * @throws AccountAlreadyExists if there's already an account with the same id
     */
    public int createAccount(String accountName) throws AccountAlreadyExists {
        int accountId = accountIdGenerator.get();

        final Account account = new Account(accountId, accountName);
        if (this.accounts.contains(account)) {
            throw new AccountAlreadyExists();
        }
        this.accounts.add(account);

        return accountId;
    }

    /**
     * Calculates the {@link Account Account's} balance based on the {@link Transaction history}
     *
     * @param accountId the {@link Account Account's} id to check the balance for
     * @return the calculated balance
     * @throws AccountNotFound if the {@link Account} does not exist
     */
    public double calculateBalance(int accountId) throws AccountNotFound {
        final Account account = findAccount(accountId);

        return transactions.stream()
                .filter(t -> account.equals(t.getOrigin()) || account.equals(t.getDestination()))
                .mapToDouble(t -> account.equals(t.getOrigin()) ? t.getAmount() * -1 : t.getAmount())
                .reduce(Double::sum)
                .orElse(0);
    }

    /**
     * Creates a new {@link Transaction} between two {@link Account Account's}
     *
     * @param originAccountId      the id of the {@link Account} that is sending the money
     * @param destinationAccountId the id of the {@link Account} that is receiving the money
     * @param amount               the amount to be sent
     * @throws InsufficientFunds if the origin {@link Account} does not have enough funds to do the {@link Transaction}
     * @throws AccountNotFound   if one of the {@link Account Accounts} does not exist
     */
    public void createTransaction(int originAccountId, int destinationAccountId, double amount) throws InsufficientFunds, AccountNotFound {
        double balance = calculateBalance(originAccountId);
        if (balance - amount < 0) {
            throw new InsufficientFunds();
        }

        final Account originAccount = findAccount(originAccountId);
        final Account destinationAccount = findAccount(destinationAccountId);

        transactions.add(new Transaction(originAccount, destinationAccount, amount));
    }

    /**
     * Deposits money in an {@link Account}
     *
     * @param accountId the id of the {@link Account} that is receiving the deposit
     * @param amount    the amount to deposit
     * @throws AccountNotFound if the {@link Account} does not exist
     */
    public void depositMoney(int accountId, double amount) throws AccountNotFound {
        final Account account = findAccount(accountId);
        transactions.add(new Transaction(null, account, amount));
    }
}
