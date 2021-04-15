package ua.training.CruiseLineSpring.exception;

public class ShipNotFoundException extends RuntimeException {
	public ShipNotFoundException(String message) {
		super(message);
	}
}
