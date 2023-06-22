package pt.esgts.exam.cli;

import pt.esgts.exam.cli.model.ScreenOption;
import pt.esgts.exam.exception.AccountNotFound;
import pt.esgts.exam.functional.BiConsumerThrows;
import pt.esgts.exam.model.Account;
import pt.esgts.exam.service.AccountService;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The screen used to execute operations on the account
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class AccountScreen extends BaseScreen {

    private final AccountService accountService;

    public AccountScreen(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run() {
        Account account = accountIdPrompt();
        if (account == null) {
            return;
        }

        accountLoggedInMenu(account);
    }

    /**
     * Ask for the account id
     *
     * @return the {@link Account}
     */
    protected Account accountIdPrompt() {
        int accountId = askInteger("Account ID", true);

        try {
            return this.accountService.findAccount(accountId);
        } catch (AccountNotFound e) {
            System.out.printf("Account not found: %d\n", accountId);
        }

        return null;
    }

    /**
     * Presents the menu with the list of actions that can be done on your account
     * Will run the SubScreens associated with the chosen option
     *
     * @param account the source {@link Account}
     */
    protected void accountLoggedInMenu(Account account) {
        AtomicBoolean quit = new AtomicBoolean(false);
        while (!quit.get()) {
            List<ScreenOption> list = List.of(new ScreenOption().setId(1).setName("Balance").setAction(() -> {
                runSubScreen(account, getBalanceSubScreen);
            }), new ScreenOption().setId(2).setName("Transfer").setAction(() -> {
                runSubScreen(account, transferSubScreen);
            }), new ScreenOption().setId(3).setName("Deposit").setAction(() -> {
                runSubScreen(account, depositSubScreen);
            }), new ScreenOption().setId(9).setName("Log out").setAction(() -> quit.set(true)));

            System.out.printf("Account: %s - %s\n", account.getId(), account.getName());
            printOptions(list);
        }
    }

    /**
     * Runs a SubScreen of the Account menu
     * The SubScreen must be a {@link BiConsumerThrows} that receives the {@link AccountService}
     * and the account id.
     *
     * @param account  the user's account
     * @param runnable the {@link BiConsumerThrows} that executes the screen
     */
    protected void runSubScreen(Account account, BiConsumerThrows<AccountService, Integer> runnable) {
        try {
            runnable.accept(accountService, account.getId());
        } catch (Exception e) {
            System.out.printf("Unexpected error: '%s'\n", e.getClass().getSimpleName());
        }

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }

    /**
     * The subScreen for getting the balance
     * Receives an {@link AccountService} and an {@link Integer} representing the account id
     */
    protected BiConsumerThrows<AccountService, Integer> getBalanceSubScreen = (accountService, accountId) -> {
        System.out.printf("Your balance is: %s\n", accountService.calculateBalance(accountId));
    };

    /**
     * The subScreen for depositing funds in the account
     * Receives an {@link AccountService} and an {@link Integer} representing the account id
     */
    protected BiConsumerThrows<AccountService, Integer> depositSubScreen = (accountService, accountId) -> {
        int amount = askInteger("Add the amount and press enter", true);
        accountService.depositMoney(accountId, amount);
    };

    /**
     * The subScreen for sending funds to another account
     * Receives an {@link AccountService} and an {@link Integer} representing the account id
     * Will ask for the target {@link Account} id.
     */
    protected BiConsumerThrows<AccountService, Integer> transferSubScreen = (accountService, accountId) -> {
        int destinationAccountId = askInteger("Destination account", true);
        int amount = askInteger("Amount to send", true);
        accountService.createTransaction(accountId, destinationAccountId, amount);
    };
}
