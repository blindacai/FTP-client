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
    public void createSocket() throws IOException {
        try{
            this.kkSocket = new Socket(this.addr, this.port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }catch (UnknownHostException e){
            Utils.errorMessage("");
        }
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

    /*
        read a line from the server response
     */
    public String readAline(String msg){
        String result = null;
        try{
            result = in.readLine();
        }catch(IOException e){
            Utils.errorMessage(msg);
            closeSocket();
            System.exit(0);
        }
        return result;
    }

    /*
        read a line from the command server response
     */
    public String readTheLine(){
        return readAline("FFFD");
    }

    /*
        read a line from the data connection response
     */
    public String readDataLine(){
        return readAline("3A7");
    }

    /*
        read some byte from the data connection response
     */
    public int readChar(byte[] buf) throws IOException{
        int toread = 0;
        try{
            toread = this.kkSocket.getInputStream().read(buf, 0, buf.length);
        }catch(IOException e){
            Utils.errorMessage("3A7");
            kkSocket.close();
        }
        return toread;
    }

    /*
        close the socket
     */
    public void closeSocket(){
        try{
            this.kkSocket.close();
        }catch(IOException e){
            System.out.println("socket closed");
        }
    }
}
