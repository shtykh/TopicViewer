package shtykh.topic.util;

/**
 * Created by shtykh on 03/04/15.
 */
public class HtmlHelper {
	private String host = "localhost";
	private String port = "8080";

	public String href(String postfix) {
		return href(postfix, null);
	}

	public String href(String postfix, String name) {
		String href = "http://" + host + ":" + port + "/" + postfix;
		if (name == null) {
			name = href;
		}
		return "<a href="+ href + ">" + name + "</a>";
	}

	public static String htmlPage(String title, String body) {
		return htmlPage(title, title, body);
	}

	public static String htmlPage(String title, String header, String body) {
		return new HtmlBuilder().title(title).header(header).body(body).build();
	}
	
	private static class HtmlBuilder {
		private String title = "Title";
		private String header = "Header";
		private String body = "Body";

		public HtmlBuilder title(String title) {
			this.title = title;
			return this;
		}

		public HtmlBuilder header(String header) {
			this.header = header;
			return this;
		}

		public HtmlBuilder body(String body) {
			this.body = body.replace("\n", "<br/>");
			return this;
		}
		
		public String build() {
			return "<html>" +
					"<title>" +
					title +
					"</title>" +
					"<body>" +
					"<h1>" +
					header +
					"</h1>" +
					body +
					"</body>" +
					"</html>";
		}
	}
}
