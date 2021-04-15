package ua.training.CruiseLineSpring.exception;

public class SpringCruiseLineException extends RuntimeException {
    public SpringCruiseLineException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringCruiseLineException(String exMessage) {
        super(exMessage);
    }
}
