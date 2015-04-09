package shtykh.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import shtykh.topic.provider.TopicReader;
import shtykh.topic.util.HtmlHelper;

/**
 * Created by shtykh on 08/04/15.
 */
public class MainApplication {
	public static void main(String[] args) throws Exception {
		TopicReader.ROOT_DIR = args[0];
		Object[] classes = new Object[]{
				HtmlHelper.class,
				TopicViewerController.class,
				TopicReader.class,
				MainApplication.class,
				};
		SpringApplication app = new SpringApplicationBuilder().
				sources(classes).build();
		app.run(args);
	}
}
