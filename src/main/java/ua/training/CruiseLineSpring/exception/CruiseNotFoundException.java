package ua.training.CruiseLineSpring.exception;

public class CruiseNotFoundException extends RuntimeException {
	public CruiseNotFoundException(String message) {
			super(message);
		}
}
