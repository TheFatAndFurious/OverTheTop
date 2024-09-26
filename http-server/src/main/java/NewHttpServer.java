import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;

public class NewHttpServer {


    static int ORIGINAL_PORT = 8080;
    static HashMap<Integer, HttpServer> activeServers = new HashMap<Integer, HttpServer>();

    public static void main(String[] var0) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int numberOfServersToBeginWith = 0;

        do {
            System.out.println("How many servers should we begin with fam ?");
            numberOfServersToBeginWith = scanner.nextInt();
                if (numberOfServersToBeginWith <= 0){
                    System.out.println("You must start at least 1 server, kiddo. Try again");
                }

        } while (numberOfServersToBeginWith <= 0);
        StartServer(numberOfServersToBeginWith);


        new Thread(() -> {
            while (true) {
                System.out.println("enter a command");
                String command = scanner.nextLine();
                ProcessCommand(command);
            }
        }).start();


    }

    static class TestHandler implements HttpHandler {

        private int serverNumber;

        public TestHandler(int serverNumber) {
            this.serverNumber = serverNumber;
        }

        public void handle(HttpExchange var1) throws IOException {
            String var2 = "We all good on server " + serverNumber;
            var1.sendResponseHeaders(200, (long) var2.length());
            OutputStream var3 = var1.getResponseBody();
            var3.write(var2.getBytes());
        }
    }

    private static void ProcessCommand(String command) {
            String[] commands = command.split(" ");
            if (commands.length == 0) {
                System.out.println("Invalid command");
                return;
            }

            String requiredAction = commands[1];
            if (requiredAction.equals("kill")) {
                KillServerArguments args = null;
                // continue here
            }
        String message = switch (command) {
            case "start" -> "let'start";
            case "kill" -> "let's kill";
            default -> "we're cooked";
        };
        System.out.println(message);

    }

    private static void StartServer(int numberOfServersToStart) throws IOException {
        for (int i = 0; i < numberOfServersToStart; i++) {
            var port = ORIGINAL_PORT + i;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new TestHandler(i));
            server.setExecutor((Executor) null);
            System.out.println("Server started on port:" + port);
            server.start();
            activeServers.put(port, server);
            System.out.println(activeServers);
        }

    }

}
