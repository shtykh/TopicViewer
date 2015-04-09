package shtykh.topic.provider;

/**
 * Created by shtykh on 08/04/15.
 */
public class TopicReaderException extends Exception {
	public TopicReaderException(String msg, Exception e) {
		super(msg + "\n" + e.getMessage());
	}

	public TopicReaderException(String msg) {
		super(msg);
	}
}
