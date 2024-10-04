import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlersManager implements HttpHandler {

    private final int serverNumber;

    public HandlersManager(int serverNumber) {
        this.serverNumber = serverNumber;
    }

    public void handle(HttpExchange var1) throws IOException {
        String var2 = "We all good on server " + serverNumber;
        var1.sendResponseHeaders(200, (long) var2.length());
        OutputStream var3 = var1.getResponseBody();
        var3.write(var2.getBytes());
    }
}