package ua.training.CruiseLineSpring.exception;

public class PortNotFoundException extends RuntimeException {
	public PortNotFoundException(String message) {
		super(message);
	}
}
