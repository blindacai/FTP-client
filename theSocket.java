import java.io.*;
import java.net.Socket;
import java.rmi.UnknownHostException;

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

    /*
        create a new socket connection
     */
    public void createSocket() throws IOException, UnknownHostException{
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

    public InputStream getinputstream() throws IOException {
        return kkSocket.getInputStream();
    }
}
