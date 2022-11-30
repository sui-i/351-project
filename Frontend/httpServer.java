import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.logging.Handler;
import java.awt.image.BufferedImage;
import java.net.http.HttpClient;

import javax.imageio.ImageIO;
import javax.swing.text.Document;

import org.w3c.dom.html.HTMLFormElement;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

public class httpServer {

	public static final String HOSTNAME = "127.0.0.1";
	public static final int PORT = 3000;

	public static void main(String[] args) throws Exception {

		HttpServer httpserver = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), 0);

		HttpContext hc = httpserver.createContext("/");
		httpserver.createContext("/static",
				new StaticFileHandler("/static/", System.getProperty("user.dir"),
						""));
		hc.setHandler(new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {

				FileInputStream fis = new FileInputStream("loginPage.html");
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, 0);
				OutputStream os = exchange.getResponseBody();
				copyStream(fis, os);

				os.close();
				fis.close();

			}
		});

		httpserver.setExecutor(null);
		httpserver.start();
		System.out.println("Started server at " + HOSTNAME + ":" + PORT + "\nListening...");

	}

	private static void copyStream(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[4096];
		int n;
		while ((n = is.read(buf)) >= 0) {
			os.write(buf, 0, n);
		}
	}

}
