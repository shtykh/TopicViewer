package shtykh.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;
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
				String.class,
				};
		ResourceLoader resourceLoader = new FileSystemXmlApplicationContext("/src/main/resources/applicationContext.xml");
		SpringApplication app = new SpringApplicationBuilder().
				sources(classes).
				resourceLoader(resourceLoader).build();
		app.run(args);
	}
}
