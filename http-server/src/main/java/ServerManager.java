import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class ServerManager {
    private PortsHandling portsHandling;

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
                System.out.println("Server started on port:" + port);
                server.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void StopSingleServer(Integer portNumber) throws Exception {
        // stop a single server
        portsHandling.releaseSinglePort(portNumber);
        System.out.println("Port has been released");
    }

    public void StopMultipleServers(Integer numberOfPortsToStop){
        // stop multiple servers
        portsHandling.releaseSinglePort();
    }
}
