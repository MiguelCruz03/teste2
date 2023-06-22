package pt.esgts.exam;

import pt.esgts.exam.cli.AccountScreen;
import pt.esgts.exam.cli.CreateAccountScreen;
import pt.esgts.exam.cli.MainScreen;
import pt.esgts.exam.service.AccountService;

/**
 * The starting class of the application
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class Main {

    /**
     * Main method
     *
     * @param args the arguments from the CLI
     */
    public static void main(String[] args) {
        final AccountService accountService = new AccountService();
        final AccountScreen accountScreen = new AccountScreen(accountService);
        final CreateAccountScreen createAccountScreen = new CreateAccountScreen(accountService);

        MainScreen screen = new MainScreen(accountScreen, createAccountScreen);

        while (true) {
            screen.run();
        }
    }
}