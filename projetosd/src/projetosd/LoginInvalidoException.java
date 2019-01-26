package projetosd;

/**
 *
 * @author Grupo 45
 */
public class LoginInvalidoException extends Exception {

    /**
     * Creates a new instance of <code>LoginInvalido</code> without detail
     * message.
     */
    public LoginInvalidoException() {
    }

    /**
     * Constructs an instance of <code>LoginInvalido</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LoginInvalidoException(String msg) {
        super(msg);
    }
}
