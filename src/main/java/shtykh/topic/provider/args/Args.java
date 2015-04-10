package shtykh.topic.provider.args;

import shtykh.topic.provider.ArgsReceiver;
import shtykh.topic.provider.ProviderException;

/**
 * Created by shtykh on 10/04/15.
 */
public abstract class Args {
	private ArgsReceiver receiver;

	public Args(ArgsReceiver receiver) {
		this.receiver = receiver;
	}

	protected void send(String[] args) throws ProviderException {
		receiver.receive(args);
	}
}
