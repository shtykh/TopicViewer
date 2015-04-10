package shtykh.topic.provider.args;

import org.springframework.beans.factory.annotation.Autowired;
import shtykh.topic.provider.ArgsReceiver;
import shtykh.topic.provider.ProviderException;

/**
 * Created by shtykh on 10/04/15.
 */
public class ArgsMock extends Args {
	private static String TEST_ROOT_DIR = "/Users/shtykh/TopicViewer/src/main/resources/testData";
	private String[] args = new String[]{TEST_ROOT_DIR};

	@Autowired
	public ArgsMock(ArgsReceiver receiver) throws ProviderException {
		super(receiver);
		send(args);
	}
}
