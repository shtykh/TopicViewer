package shtykh.topic.util;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by shtykh on 03/04/15.
 */
public class HtmlHelper {

	private final String scheme;
	private final String host;
	private final int port;

	public HtmlHelper(String scheme, String host, int port) {
		this.scheme = scheme;
		this.host = host;
		this.port = port;
	}

	public URIBuilder uriBuilder(String postfix) throws URISyntaxException {
		return new URIBuilder()
				.setScheme(scheme)
				.setHost(host)
				.setPort(port)
				.setPath(postfix);
	}

	public static String href(URI uri) {
		return href(uri, null);
	}

	public static String href(URI uri, String name) {
		String href = uri.toString();
		if (name == null) {
			name = href;
		}
		return "<a href=" + href + ">" + name + "</a>";
	}

	public static String htmlPage(String title, String body) {
		return new HtmlBuilder()
				.title(title)
				.body(body)
				.build();
	}

	public static String htmlPage(String title, String header, String body) {
		return new HtmlBuilder()
				.title(title)
				.header(header)
				.body(body)
				.build();
	}

	public static String errorPage(String msg) {
		return htmlPage("Error", msg);
	}

	private static class HtmlBuilder {
		private static final String DEFAULT_TITLE = "Untitled";
		private static final String DEFAULT_BODY = "";
		private String title;
		private String header;
		private String body;

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
			assignDefaultIfNull();
			return "<html><title>" +
					title +
					"</title><body><h1>" +
					header +
					"</h1>" +
					body +
					"</body></html>";
		}

		private void assignDefaultIfNull() {
			if (title == null) {
				title = DEFAULT_TITLE;
			}
			if (header == null) {
				header = title;
			}
			if (body == null) {
				body = DEFAULT_BODY;
			}
		}
	}
}
