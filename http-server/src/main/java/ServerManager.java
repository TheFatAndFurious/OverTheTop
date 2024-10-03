import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.Executor;

public class ServerManager {
    private PortsHandling portsHandling;
    private HashMap<Integer, HttpServer> serverHashMap = new HashMap<>();

    public ServerManager(PortsHandling portsHandling){
        this.portsHandling = portsHandling;
    }

    public void StartServer(Integer numberOfServers) throws IOException {
        for (int i = 0; i < numberOfServers; i++) {
            try {
                var port = portsHandling.reservePort();
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", new NewHttpServer.TestHandler(i));
                server.setExecutor((Executor) null);
                server.start();
                serverHashMap.put(port, server);
                System.out.println("Server started on port:" + port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void StopSingleServer(Integer portNumber) throws Exception {
        if (!serverHashMap.containsKey(portNumber)){
            System.out.println("No running server using that port");
            return;
        }
        serverHashMap.forEach(
                (key, value)
                        -> if(Integer == portNumber)
                );
        portsHandling.releaseSinglePort(portNumber);
        System.out.println("Port has been released");
    }

    public void StopMultipleServers(Integer numberOfPortsToStop){
        // stop multiple servers
        portsHandling.releaseMultiplePorts(numberOfPortsToStop);
        // TODO: add the number of servers killed for a better feedback
        System.out.println("Servers have been shut down");
    }
}
