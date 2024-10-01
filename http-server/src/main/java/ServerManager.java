public class ServerManager {
    private PortsHandling portsHandling;

    public ServerManager(PortsHandling portsHandling){
        this.portsHandling = portsHandling;
    }

    public void StartServer(Integer numberOfServers){
        // start servers
    }

    public void StopSingleServer(Integer portNumber){
        // stop a single server
    }

    public void StopMultipleServers(Integer numberOfPortsToStop){
        // stop multiple servers
    }
}
