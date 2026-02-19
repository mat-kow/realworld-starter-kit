package pl.teo.realworldapp.app.exception;

public class ApiUnAuthorizedException extends RuntimeException {
    public ApiUnAuthorizedException(String message) {
        super(message);
    }
}
