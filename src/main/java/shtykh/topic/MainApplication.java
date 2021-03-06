package shtykh.topic;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import shtykh.topic.provider.CachedTopicReader;
import shtykh.topic.provider.args.CommandLineArgs;

/**
 * Created by shtykh on 08/04/15.
 */
@EnableAutoConfiguration
public class MainApplication {
	private static final Logger log = Logger.getLogger(MainApplication.class);

	public static void main(String[] args) throws Exception {
		try {
			Object[] classes = new Object[]{
					TopicViewer.class,
					//TopicReader.class,
					CachedTopicReader.class,
					CommandLineArgs.class,
					MainApplication.class,
			};
			SpringApplication app = new SpringApplicationBuilder()
					.sources(classes)
					.build();
			app.run(args);
			log.info("Application launched successfully");
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
}
