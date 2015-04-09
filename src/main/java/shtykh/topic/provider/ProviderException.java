package shtykh.topic.provider;

/**
 * Created by shtykh on 08/04/15.
 */
public class ProviderException extends Exception {
	public ProviderException(String msg, Exception e) {
		super(msg + "\n" + e.getMessage());
	}

	public ProviderException(String msg) {
		super(msg);
	}
}
