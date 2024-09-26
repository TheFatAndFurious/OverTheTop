import jdk.jfr.Description;

public class KillServerArguments {

    private boolean isSpecificPort;
    private int port;
    private int numberOfServers;

    // Constructor to kill a port specific server
    public KillServerArguments(int portNumber){
        this.isSpecificPort = true;
        this.port = portNumber;
        this.numberOfServers = 0;
    }

    // Constructor to kill multiple servers
    public KillServerArguments(int numberOfServers){
        this.isSpecificPort = false;
        this.port = -1;
        this.numberOfServers = numberOfServers;
    }

    public boolean isSpecificPort() {
        return isSpecificPort;
    }

    public int getPort() {
        return port;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }
}
