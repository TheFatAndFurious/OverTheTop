import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class PortsHandling {
    // ports range
    private final int FIRST_PORT = 8080;
    private final int LAST_PORT = 9999;
    // available ports
    private Queue<Integer> availablePorts;

    public PortsHandling(){
        for (int i = FIRST_PORT; i <= LAST_PORT; i++){
            availablePorts.add(i);
        }
    }

    public synchronized int reservePort() throws Exception {
        Integer port = availablePorts.poll();
        if (port != null) {
            return  port;
        } else {
            throw new Exception("No ports available");
        }
    }


}
