public class BadTransactionException extends Exception {
	public int amount;

	public BadTransactionException(int amount) {
		super("Invalid deposit amount");
	}
}