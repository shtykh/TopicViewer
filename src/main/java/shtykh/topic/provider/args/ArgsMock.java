package shtykh.topic.provider.args;

import org.springframework.beans.factory.annotation.Autowired;
import shtykh.topic.provider.ArgsReceiver;
import shtykh.topic.provider.ProviderException;

/**
 * Created by shtykh on 10/04/15.
 */
public class ArgsMock extends Args {
	private String[] args = new String[]{"/Users/shtykh/TopicViewer/testData"};

	@Autowired
	public ArgsMock(ArgsReceiver receiver) throws ProviderException {
		super(receiver);
		send(args);
	}
}
