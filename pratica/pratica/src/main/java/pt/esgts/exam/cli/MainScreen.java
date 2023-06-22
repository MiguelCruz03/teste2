package pt.esgts.exam.cli;

import pt.esgts.exam.cli.model.ScreenOption;
import pt.esgts.exam.service.AccountService;

import java.util.List;

/**
 * The application's start screen
 *
 * @author Bruno Jesus
 */
public class MainScreen extends BaseScreen {

    private final AccountScreen accountScreen;
    private final CreateAccountScreen createAccountScreen;

    public MainScreen(AccountScreen accountScreen,
                      CreateAccountScreen createAccountScreen) {

        this.accountScreen = accountScreen;
        this.createAccountScreen = createAccountScreen;
    }

    @Override
    public void run() {
        printOptions(
                List.of(
                        new ScreenOption().setId(1).setName("Log in").setAction(accountScreen),
                        new ScreenOption().setId(2).setName("Create account").setAction(createAccountScreen),
                        new ScreenOption().setId(9).setName("Quit").setAction(() -> System.exit(0))
                )
        );
    }
}
