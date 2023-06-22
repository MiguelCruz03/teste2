package pt.esgts.exam.exception;

/**
 * Exceção lançada quando uma conta já existe.
 */
public class AccountAlreadyExists extends Exception {

    /**
     * Construtor padrão da exceção.
     */
    public AccountAlreadyExists() {
    }

    /**
     * Construtor da exceção com uma mensagem específica.
     *
     * @param message A mensagem de erro específica.
     */
    public AccountAlreadyExists(String message) {
        super(message);
    }
}
