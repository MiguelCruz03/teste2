package pt.esgts.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import pt.esgts.exam.exception.AccountAlreadyExists;
import pt.esgts.exam.exception.AccountNotFound;
import pt.esgts.exam.exception.InsufficientFunds;
import pt.esgts.exam.model.Account;
import pt.esgts.exam.model.Transaction;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService subject;
    private Set<Account> accounts;
    private List<Transaction> transactions;
    private AccountIdGenerator accountIdGenerator;

    @BeforeEach
    void setUp() {
        accounts = new HashSet<>(
                Set.of(
                        new Account(1, "account_1"),
                        new Account(2, "account_2"),
                        new Account(3, "account_3")
                )
        );

        transactions = new ArrayList<>();
        accountIdGenerator = Mockito.mock(AccountIdGenerator.class);

        subject = new AccountService(transactions, accounts, accountIdGenerator);
    }

    private static Stream<Arguments> existingAccountArguments() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3)
        );
    }

    @ParameterizedTest
    @MethodSource("existingAccountArguments")
    void findAccount(int id) throws AccountNotFound {
        Account account = subject.findAccount(id);
        assertTrue(accounts.contains(account), "Returned account that should not exist");
    }

    @Test
    void findAccountNotExistent() {
        assertThrows(AccountNotFound.class, () ->
                subject.findAccount(5)
        );
    }

    @Test
    void createAccount() throws AccountAlreadyExists {
        Mockito.when(accountIdGenerator.get()).thenReturn(10);

        int accountId = subject.createAccount("test_create_account_1");

        assertEquals(4, accounts.size());
        assertEquals(accountId, 10);
        assertTrue(accounts.contains(new Account(accountId, "test_create_account_1")));
    }

    @Test
    void createTransactionAccountNotFound() {
        final Account acc1 = new Account(1, null);
        final Account acc2 = new Account(2, null);

        this.transactions.add(new Transaction(null, acc1, 100));
        this.transactions.add(new Transaction(null, acc2, 100));

        assertThrows(AccountNotFound.class, () ->
                subject.createTransaction(10, 2, 10)
        );
        assertThrows(AccountNotFound.class, () ->
                subject.createTransaction(1, 20, 10)
        );
    }

    @Test
    void depositMoney() throws AccountNotFound {
        subject.depositMoney(1, 10);

        assertEquals(1, transactions.size());

        final Transaction transaction = transactions.get(0);
        assertNull(transaction.getOrigin());
        assertEquals(10, transaction.getAmount());

        final Account acc = new Account(1, null);
        assertEquals(acc, transaction.getDestination());
    }

    @Test
    void depositMoneyAccountNotFound() {
        assertThrows(AccountNotFound.class, () ->
                subject.depositMoney(10, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("existingAccountArguments")
    void createAccountAlreadyExists(int id) throws AccountNotFound {
        //TODO: not implemented yet
        throw new RuntimeException("Not implemented yet");
    }

    @Test
    void calculateBalanceNotExistingAccount() throws AccountNotFound {
        //TODO: not implemented yet
        throw new RuntimeException("Not implemented yet");
    }

    @Test
    void createTransaction() throws InsufficientFunds, AccountNotFound {
        //TODO: not implemented yet
        throw new RuntimeException("Not implemented yet");
    }

    @Test
    void createTransactionInsufficientFunds() {
        //TODO: not implemented yet
        throw new RuntimeException("Not implemented yet");
    }

    @Test
    void calculateBalance() throws AccountNotFound {
        //TODO: not implemented yet
        throw new RuntimeException("Not implemented yet");
    }
}