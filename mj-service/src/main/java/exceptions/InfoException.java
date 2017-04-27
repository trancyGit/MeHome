package exceptions;

/**
 * Created by dell on 2015/11/29.
 */
public class InfoException extends RuntimeException {
    public InfoException() {
    }

    public InfoException(Throwable t) {
        super(t);
    }

    public InfoException(String msg, Throwable t) {
        super(msg, t);
    }

    public InfoException(String msg) {
        super(msg);
    }
}

