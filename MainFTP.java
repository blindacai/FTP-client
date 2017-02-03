import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by linda on 2/2/2017.
 */
public class MainFTP {
    public static void main(String [] args) throws IOException {
        //String hostname = args[0];
        String hostname = "ftp.cs.ubc.ca";
        int port = 21;
        theSocket kkSocket = new theSocket(hostname, port);
        kkSocket.createSocket();

        String fromServer;
        String fromUser;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  // accept user input

        while((fromServer = kkSocket.getin().readLine()) != null){
            System.out.println(fromServer);
            fromUser = stdIn.readLine();
            if(fromUser.equals("user")){
                kkSocket.getout().println("user anonymous");
            }
        }
    }
}
