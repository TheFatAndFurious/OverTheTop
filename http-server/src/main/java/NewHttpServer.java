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
        InputManager inputManager = new InputManager(serverManager);
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
                try {
                    inputManager.ProcessCommand(command);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
