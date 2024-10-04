import java.util.ArrayList;

public class InputManager {

    ServerManager manager;

    public InputManager(ServerManager manager){
        this.manager = manager;
    }

    private String[] ProcessInput(String input) {
        String[] commands = input.split(" ");
        if (commands.length == 0) {
            System.out.println("Invalid command");
            return null;
        }
        return commands;
    }

    public void ProcessCommand(String input) throws Exception{
        String[] commands = ProcessInput(input);
        if (commands[0] == null){
            throw new Exception("Empty commands");
        }
        String requiredAction = commands[0].toLowerCase();

        switch (requiredAction){
            case "kill":
                if(commands.length == 2){
                    try{
                        int numberOfServersToKill = Integer.parseInt(commands[1]);
                        manager.StopMultipleServers(numberOfServersToKill);
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid number: " + commands[1]);
                    }
                }
                else if (commands.length == 1){
                    manager.StopSingleServer();
                }
                else {
                    System.out.println("Usage is: kill [number of servers]");
                }
                break;
            case "start":
                if (commands.length == 2){
                    try {
                        int numberOfServersToStart = Integer.parseInt(commands[1]);
                        manager.StartServer(numberOfServersToStart);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number: " + commands[1]);
                    }
                }
                else if (commands.length == 1){
                    manager.StartServer(1);
                }
                else {
                    System.out.println("Usage is: kill [number of servers]");
                }
                break;
        }
    }
}