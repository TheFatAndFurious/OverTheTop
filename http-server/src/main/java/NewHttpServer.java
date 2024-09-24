import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executor;

public class NewHttpServer {


    public static void main(String[] var0) throws IOException {

        final int ORIGINAL_PORT = 8080;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many servers should we start fam ?");
        int numberOfServers = scanner.nextInt();

        for (int i = 0; i < numberOfServers; i++) {
            var port = ORIGINAL_PORT + i;
            HttpServer var1 = HttpServer.create(new InetSocketAddress(port), 0);
            var1.createContext("/", new TestHandler(i));
            var1.setExecutor((Executor) null);
            System.out.println("Server started on port:" + port);
            var1.start();
        }

    }
        static class TestHandler implements HttpHandler {

            private int serverNumber;

            public TestHandler(int serverNumber){
                this.serverNumber = serverNumber;
            }

            public void handle(HttpExchange var1) throws IOException {
                String var2 = "We all good on server " + serverNumber;
                var1.sendResponseHeaders(200, (long) var2.length());
                OutputStream var3 = var1.getResponseBody();
                var3.write(var2.getBytes());
            }
        }
}