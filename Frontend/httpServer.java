import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.imageio.ImageIO;
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
				Headers h = exchange.getResponseHeaders();
				
				String line;
				String r = "";
				// System.out.println(exchange.getRequestBody());
				
				File newFile = new File("C:/Users/Mouni/Desktop/351/351-project/Frontend/loginPage.html");
				BufferedImage logo = ImageIO.read(new File("C:/Users/Mouni/Desktop/351/351-project/Frontend/Items/Hotel logo.png"));
				ByteArrayOutputStream logoBytes = new ByteArrayOutputStream();
				ImageIO.write(logo, "png", logoBytes);
				byte[] data = logoBytes.toByteArray();
				String s = new String(data, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
				
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					r += line;
				}
				h.add("Content-Type", "text/html");

				exchange.sendResponseHeaders(200, r.length());
				//exchange.sendResponseHeaders(200, 0);
				OutputStream os = exchange.getResponseBody();
				System.out.println(s);
				br.close();
				os.write(r.getBytes());
				os.write(s.getBytes());
				os.close();
			}
		});
		
		httpserver.setExecutor(null);
		httpserver.start();
	}

}