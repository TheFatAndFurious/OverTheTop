import java.util.ArrayList;

public class PortsHandling {
    // ports range
    private final int FIRST_PORT = 8080;
    private final int LAST_PORT = 9999;
    // available ports
    private ArrayList<Integer> availablePorts = new ArrayList<>();

    public PortsHandling(){
        for (int i = FIRST_PORT; i <= LAST_PORT; i++){
            availablePorts.add(i);
        }
    }
}
