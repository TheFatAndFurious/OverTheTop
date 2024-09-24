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

        Scanner scanner = new Scanner(System.in);
        new Thread(()-> {
            while(true){
                System.out.println("enter a command");
                String command = scanner.nextLine();
            }
        }).start();

        final int ORIGINAL_PORT = 8080;
        System.out.println("How many servers should we start fam ?");
        int numberOfServers = scanner.nextInt();


        for (int i = 0; i < numberOfServers; i++) {
            var port = ORIGINAL_PORT + i;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new TestHandler(i));
            server.setExecutor((Executor) null);
            System.out.println("Server started on port:" + port);
            server.start();
        }
        System.out.println("what do we do now ?");

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