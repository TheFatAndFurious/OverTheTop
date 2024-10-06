import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
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
        Integer portReleased = portsHandling.releasePort();
        System.out.println("stopSingleServer portReleased is: " + portReleased);
        if (!serverHashMap.containsKey(portReleased)){
            System.out.println("Targeted port to kill not found");
        }
        HttpServer serverToKill = serverHashMap.get(portReleased);
        serverToKill.stop(0);
        serverHashMap.remove(portReleased);
        System.out.println("Port " + portReleased + " has been released");
    }

    public void StopMultipleServers(Integer numberOfPortsToStop) throws Exception{
        Integer maxNumberOfPorts = portsHandling.LAST_PORT - portsHandling.FIRST_PORT;
        if (numberOfPortsToStop > maxNumberOfPorts){
            System.out.println("You can not close more than " + maxNumberOfPorts);
            return;
        }
        if (numberOfPortsToStop <= 0 ){
            System.out.println("You need to close at least 1 port");
            return;
        }
        ArrayList<Integer> portsToRelease = portsHandling.releasePort(numberOfPortsToStop);
        int serversKilledCounter = 0;
        System.out.println("serverHasmap is " + serverHashMap);
        System.out.println("portsToRelease = " + portsToRelease);
        for (Integer port : portsToRelease) {
            System.out.println("Port is: " + port);
            if (serverHashMap.containsKey(port)){
                HttpServer serverToKIll = serverHashMap.get(port);
                serverToKIll.stop(0);
                serverHashMap.remove(port);
                serversKilledCounter ++;
            } else {
                System.out.println("Server not found");
            }
        }
        if (serversKilledCounter == 0) {
            System.out.println("No servers have been shut down");
        } else {
            System.out.println(serversKilledCounter + " servers have been shut down");
        }
    }
}
