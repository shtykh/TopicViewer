package shtykh.topic.provider.args;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import shtykh.topic.provider.ArgsReceiver;

/**
 * Created by shtykh on 10/04/15.
 */
public class CommandLineArgs extends Args implements CommandLineRunner {

	@Autowired
	public CommandLineArgs(ArgsReceiver receiver) {
		super(receiver);
	}

	@Override
	public void run(String... args) throws Exception {
		send(args);
	}
}
