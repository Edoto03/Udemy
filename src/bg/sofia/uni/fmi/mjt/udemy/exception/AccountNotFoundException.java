package bg.sofia.uni.fmi.mjt.udemy.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
