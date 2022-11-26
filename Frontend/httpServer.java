import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

public class httpServer {
	public static void main(String[] args) throws Exception {
		HttpServer httpserver = HttpServer.create(new InetSocketAddress(3000), 0);
		HttpContext hc = httpserver.createContext("/");
		httpserver.createContext("/static", new StaticFileHandler("/file:///", "C:/Users/Mouni/Desktop/351/351-project/Frontend", "loginPage_stylesheet.css"));
		hc.setHandler(new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				Headers h = exchange.getResponseHeaders();
				String line;
				String r = "";
				// System.out.println(exchange.getRequestBody());

				File newFile = new File("C:/Users/Mouni/Desktop/351/351-project/Frontend/loginPage.html");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					r += line;
				}
				h.add("Content-Type", "text/html");

				exchange.sendResponseHeaders(200, r.length());

				OutputStream os = exchange.getResponseBody();
				br.close();
				os.write(r.getBytes());
				os.close();
			}
		});
		
		httpserver.setExecutor(null);
		httpserver.start();
	}

}