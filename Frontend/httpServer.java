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
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import javax.imageio.ImageIO;
import javax.swing.text.Document;

import java.awt.image.BufferedImage;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

public class httpServer {
	public static void main(String[] args) throws Exception {

		HttpServer httpserver = HttpServer.create(new InetSocketAddress(3000), 0);

		HttpContext hc = httpserver.createContext("/");
		hc.setHandler(new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {

				FileInputStream fis = new FileInputStream("loginPage.html");
				FileInputStream fis1 = new FileInputStream("loginPage_stylesheet.css");
				exchange.sendResponseHeaders(200, 0);
				exchange.getResponseHeaders().add("Content-Type", "text/html");
				// exchange.sendResponseHeaders(200, 0);
				// exchange.getResponseHeaders().add("Content-Type", "text/css");
				OutputStream os = exchange.getResponseBody();
				copyStream(fis, os);
				copyStream(fis1, os);
				os.close();

				/*
				 * fis1.readAllBytes();
				 * copyStream(fis1, os);
				 * copyStream(fis, os);
				 * os.close();
				 * fis.close();
				 * fis1.close();
				 */

			}
		});

		httpserver.setExecutor(null);
		httpserver.start();

	}

	private static void copyStream(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[4096];
		int n;
		while ((n = is.read(buf)) >= 0) {
			os.write(buf, 0, n);
		}
	}

}
