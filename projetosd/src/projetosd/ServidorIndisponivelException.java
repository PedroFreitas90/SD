package projetosd;

/**
 *
 * @author Grupo 45
 */
public class ServidorIndisponivelException extends Exception {

    /**
     * Creates a new instance of <code>ServidorIndisponivel</code> without
     * detail message.
     */
    public ServidorIndisponivelException() {
    }

    /**
     * Constructs an instance of <code>ServidorIndisponivel</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServidorIndisponivelException(String msg) {
        super(msg);
    }
}
