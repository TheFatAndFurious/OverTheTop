import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class NewHttpServer {

    public static void main(String[] var0) throws IOException {
        HttpServer var1 = HttpServer.create(new InetSocketAddress(8080), 0);
        var1.createContext("/", new TestHandler());
        var1.setExecutor((Executor)null);
        System.out.println("Server started on port 8080");
        var1.start();
    }

    static class TestHandler implements HttpHandler {
        TestHandler() {
        }

        public void handle(HttpExchange var1) throws IOException {
            String var2 = "We all good";
            var1.sendResponseHeaders(200, (long)var2.length());
            OutputStream var3 = var1.getResponseBody();
            var3.write(var2.getBytes());
        }
    }
}