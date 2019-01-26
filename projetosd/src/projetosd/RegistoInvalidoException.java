package projetosd;

/**
 *
 * @author Grupo 45
 */
public class RegistoInvalidoException extends Exception {

    /**
     * Creates a new instance of <code>RegistoInvalidoException</code> without
     * detail message.
     */
    public RegistoInvalidoException() {
    }

    /**
     * Constructs an instance of <code>RegistoInvalidoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RegistoInvalidoException(String msg) {
        super(msg);
    }
}
