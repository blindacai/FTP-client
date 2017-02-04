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
        Command command = new Command();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  // accept user input

        while(true){
            do{
                fromServer = kkSocket.getin().readLine();
                System.out.println(fromServer);
            }while(Utils.notlastline(fromServer));

            System.out.print("csftp> ");
            fromUser = stdIn.readLine();
            command.setUserinput(fromUser);

            if(command.commandNeed()){
                kkSocket.getout().println(command.getFTPcommand());
            }
        }
    }
}
