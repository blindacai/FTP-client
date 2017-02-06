import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by linda on 2/2/2017.
 */
public class theSocket {
    private String addr;
    private int port;
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;

    public theSocket(String addr, int port) {
        this.addr = addr;
        this.port = port;

    }

    // may need try catch to deal with exceptions
    public void createSocket() throws IOException {
        this.kkSocket = new Socket(this.addr, this.port);
        out = new PrintWriter(kkSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
    }

    public PrintWriter getout(){
        return this.out;
    }

    public BufferedReader getin(){
        return this.in;
    }

    public Socket getKkSocket(){
        return this.kkSocket;
    }
}
