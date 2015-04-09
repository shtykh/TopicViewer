package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import shtykh.topic.provider.CachedTopicReader;
import shtykh.topic.provider.TopicReader;

/**
 * Created by shtykh on 08/04/15.
 */
@EnableAutoConfiguration
public class MainApplication {
	private static final Logger log = Logger.getLogger(MainApplication.class);

	public static void main(String[] args) throws Exception {
		try {
			if (args.length < 1) {
				throw new Exception("args could not be empty!");
			}
			TopicReader.rootDirPath = args[0];
			Object[] classes = new Object[]{
					TopicViewer.class,
					CachedTopicReader.class,
					MainApplication.class,
			};
			SpringApplication app = new SpringApplicationBuilder().
					sources(classes).
					build();
			app.run(args);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
