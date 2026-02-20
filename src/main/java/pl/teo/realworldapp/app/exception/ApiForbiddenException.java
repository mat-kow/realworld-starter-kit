package pl.teo.realworldapp.app.exception;

public class ApiForbiddenException extends RuntimeException {
    public ApiForbiddenException(String message) {
        super(message);
    }
}
