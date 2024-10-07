import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
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
                server.createContext("/", new HandlersManager(i));
                server.setExecutor((Executor) null);
                server.start();
                serverHashMap.put(port, server);
                System.out.println("Server started on port:" + port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void StopSingleServer() throws Exception {
        ServerStopper(1);
    }

    public void StopMultipleServers(Integer numberOfPortsToStop) throws Exception{
        ServerStopper(numberOfPortsToStop);
    }

    public void StopAllServers() throws Exception{
        ServerStopper(serverHashMap.size());
    }

    private void ServerStopper(int numberOfPortsToClose) throws Exception {
        if(serverHashMap.isEmpty()){
            System.out.println("No servers to shutdown");
            return;
        }
        if(numberOfPortsToClose < 0 ){
            System.out.println("Negative numbers won't be tolerated here buddy");
            return;
        }
        List<Integer> portsToStop = new ArrayList<>(serverHashMap.keySet());
        portsToStop.sort(Collections.reverseOrder());

        int serversStopped = 0;
        for (int port : portsToStop){
            if (serversStopped == numberOfPortsToClose){
                break;
            }
            HttpServer server = serverHashMap.get(port);
            if (server != null){
                server.stop(0);
                serverHashMap.remove(port);
                portsHandling.releasePort(port);
                serversStopped ++;
                System.out.println("Stopped server on port: " + port);
            }
        }
        System.out.println("Total servers stopped: " + serversStopped);
    }

}
