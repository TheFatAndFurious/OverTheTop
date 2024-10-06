import java.util.*;

// TODO: find documentation regarding the ports range available on localhost

public class PortsHandling {
    // ports range
    public int FIRST_PORT = 8080;
    public int LAST_PORT = 9999;
    // available ports
    private Deque<Integer> availablePorts = new LinkedList<>()

    {
    };

    public PortsHandling(){

        for (int i = FIRST_PORT; i <= LAST_PORT; i++){
            availablePorts.addLast(i);
        }
    }

    public synchronized int reservePort() throws Exception {
        Integer port = availablePorts.pollFirst();
        if (port != null) {
            return port;
        } else {
            throw new Exception("No ports available");
        }
    }

    public synchronized Integer releasePort() throws Exception{
        Integer portToRelease = availablePorts.peekFirst();
        System.out.println("releasePort portToRelease == " + portToRelease);
        if (portToRelease != null && portToRelease != FIRST_PORT){
            portToRelease -= 1;
            System.out.println("port to release -1 == " + portToRelease);
            availablePorts.addFirst(portToRelease);
            System.out.println(availablePorts);
            return portToRelease;
        } else {
            throw new Exception("No port to release");
        }
    }

    public synchronized ArrayList<Integer> releasePort(Integer numberOfPorts) throws Exception{
        if (numberOfPorts <= 0){
            System.out.println("You need to kill at least 1 server buddy");
            return null;
        }
        ArrayList<Integer> portsReleased = new ArrayList<>();
        for (int i = numberOfPorts; i > 0; i--){
            Integer headOfQueue = availablePorts.peekFirst();
            if (headOfQueue == null){
                headOfQueue = LAST_PORT + 1;
            }
            if(headOfQueue == FIRST_PORT) {
                System.out.println("No servers to kill");
                return null;
            }
            availablePorts.addFirst(headOfQueue - 1);
            portsReleased.add(availablePorts.peekFirst() -1);
        }
            return portsReleased;
    }

    public synchronized boolean isPortAvailable(int portNumber) {
        return availablePorts.contains(portNumber);
    }
}
