package pl.teo.realworldapp.app.exception;

public class ApiNonUniqueValueException extends RuntimeException {
    public ApiNonUniqueValueException(String message) {
        super(message);
    }
}
