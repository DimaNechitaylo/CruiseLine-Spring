package ua.training.CruiseLineSpring.exception;

public class OrderNotFoundException  extends RuntimeException {
	public OrderNotFoundException(String message) {
		super(message);
	}
}
