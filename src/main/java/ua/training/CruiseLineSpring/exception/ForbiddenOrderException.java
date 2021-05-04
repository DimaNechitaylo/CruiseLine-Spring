package ua.training.CruiseLineSpring.exception;

public class ForbiddenOrderException extends RuntimeException {
	public ForbiddenOrderException(String message) {
		super(message);
	}
}
