package pt.esgts.exam.cli;

import pt.esgts.exam.exception.AccountAlreadyExists;
import pt.esgts.exam.service.AccountService;

/**
 * Tela usada para criar uma nova conta.
 * Extende a classe BaseScreen.
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class CreateAccountScreen extends BaseScreen {

    private final AccountService accountService;

    /**
     * Construtor da tela de criação de conta.
     *
     * @param accountService O serviço de conta a ser utilizado.
     */
    public CreateAccountScreen(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Executa a tela de criação de conta.
     * Solicita um nome para a conta e chama o serviço para criar a conta.
     * Exibe mensagens apropriadas dependendo do resultado da operação.
     */
    @Override
    public void run() {
        String accountName = askText("Escolha um nome para a conta", true);

        try {
            int accountId = accountService.createAccount(accountName);
            System.out.printf("Conta criada com o ID '%d'!\n", accountId);

        } catch (AccountAlreadyExists e) {
            System.out.printf("Erro inesperado (%s), por favor, tente novamente\n", e.getClass().getSimpleName());
        }

        System.out.print("Pressione enter para continuar...");
        scanner.nextLine();
    }
}
