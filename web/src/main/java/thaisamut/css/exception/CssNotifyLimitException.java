package thaisamut.css.exception;

public class CssNotifyLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5502261752760632545L;

	public CssNotifyLimitException(){super("Over limit Exception.");}
	public CssNotifyLimitException(String message) {
		super(message);
	}
}
