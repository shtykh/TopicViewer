package shtykh.topic.provider;

/**
 * Created by shtykh on 10/04/15.
 */
public interface ArgsReceiver {
	void receive(String[] args) throws ProviderException;
}
