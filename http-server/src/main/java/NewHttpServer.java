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
    static int LAST_PORT = 9999;
    static HashMap<Integer, HttpServer> activeServers = new HashMap<Integer, HttpServer>();
    static ArrayList<Integer> availableServers = new ArrayList<>();


    public static void main(String[] var0) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int numberOfServersToBeginWith = 0;
        PortsHandling portsHandling = new PortsHandling();
        ServerManager serverManager = new ServerManager(portsHandling);
        for (int i = ORIGINAL_PORT; i < LAST_PORT; i++){
            availableServers.add(i);
        }
        do {
            System.out.println("How many servers should we begin with fam ?");
            numberOfServersToBeginWith = scanner.nextInt();
                if (numberOfServersToBeginWith <= 0){
                    System.out.println("You must start at least 1 server, kiddo. Try again");
                }

        } while (numberOfServersToBeginWith <= 0);
        serverManager.StartServer(numberOfServersToBeginWith);


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

        String requiredAction = commands[1].toLowerCase();
        //KillServerArguments args = null;
        if (requiredAction.equals("kill")) {
            // kill some servers
            // check if there is a -p flag or an int
            // -p flag means a specific port is targeted for shutdown
            // just an int means we will shut down multiple servers at once
            // we will target the highest ports for shutdown if no port is specified
        }
        if (requiredAction.equals("start")){
            // do start stuff
            // check whether we need to refactor StartServer method or use a new one
        } else {
            System.out.println("Wrong command, use either kill or start");
        }
    }


}
