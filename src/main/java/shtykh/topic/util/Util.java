package shtykh.topic.util;

/**
 * Created by shtykh on 03/04/15.
 */
public class Util {
	private static String host = "localhost";
	private static String port = "8080";

	private Util(){};

	public static String href(String suffix) {
		return href(suffix, null);
	}

	public static String href(String suffix, String name) {
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
						body + 
					"</body>" +
				"</html>";
	}
}
