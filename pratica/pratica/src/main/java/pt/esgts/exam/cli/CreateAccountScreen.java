package pt.esgts.exam.cli;

import pt.esgts.exam.exception.AccountAlreadyExists;
import pt.esgts.exam.service.AccountService;

/**
 * The screen used to create a new account
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class CreateAccountScreen extends BaseScreen {

    private final AccountService accountService;

    public CreateAccountScreen(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run() {
        String accountName = askText("Choose an account name", true);

        try {
            int accountId = accountService.createAccount(accountName);
            System.out.printf("Account created with id '%d'!\n", accountId);

        } catch (AccountAlreadyExists e) {
            System.out.printf("Unexpected error (%s), please try again\n", e.getClass().getSimpleName());
        }

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }
}
