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

    /*
        create a new socket connection
     */
    public void createSocket(String whichConnection){
        try{
            this.kkSocket = new Socket(this.addr, this.port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }catch (IOException e){
            if(whichConnection.equals("command")){
                Utils.errorMessage("FFFC");
                System.exit(0);
            }
            else{
                Utils.errorMessage("3A2");
                this.closeSocket();
            }
        }
    }

    public PrintWriter getout(){
        return this.out;
    }

    public BufferedReader getin(){
        return this.in;
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

            if(msg.equals("FFFD"))
                System.exit(0);
        }
        return result;
    }

    /*
        read a line from the command connection response
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
    public int readChar(byte[] buf) {
        int toread = 0;
        try{
            toread = this.kkSocket.getInputStream().read(buf, 0, buf.length);
        }catch(IOException e){
            Utils.errorMessage("3A7");
            closeSocket();
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
