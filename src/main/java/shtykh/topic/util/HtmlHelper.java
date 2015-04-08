package shtykh.topic.util;

/**
 * Created by shtykh on 03/04/15.
 */
public class HtmlHelper {
	private final String host = "localhost";
	private final String port = "8080";

	public HtmlHelper() {
	}

	public String href(String suffix) {
		return href(suffix, null);
	}

	public String href(String suffix, String name) {
		String href = "http://" + host + ":" + port + "/" + suffix;
		if (name == null) {
			name = href;
		}
		return "<a href="+ href + ">" + name + "</a>";
	}

	public static String htmlPage(String title, String body) {
		return htmlPage(title, title, body);
	}

	public static String htmlPage(String title, String header, String body) {
		return ("<html>" +
					"<title>" +
						title +
					"</title>" +
					"<body>" +
						"<h1>" +
							header +
						"</h1>") +
							body.replace("\n", "<br/>") +
					"</body>" +
				"</html>";
	}
}
