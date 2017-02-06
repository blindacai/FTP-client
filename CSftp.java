import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

/**
 * Created by linda on 2/2/2017.
 */
public class CSftp {
    public static void main(String [] args) throws IOException {
        String hostname= null;
        int port;

        /*
            default port is 21 if not specified
         */
        try{
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }catch(ArrayIndexOutOfBoundsException e){
            port = 21;
        }

        theSocket kkSocket = new theSocket(hostname, port);
        try{
            kkSocket.createSocket();
        }catch(UnknownHostException e){
            System.out.println("0xFFFC Control connection to " + hostname + " on port " + port + " failed to open");
            System.exit(0);
        }

        Command command = new Command();
        fromServer server = new fromServer(kkSocket, command);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  // accept user input

        server.printResponse();
        String fromUser;
        while(true){
            System.out.print("csftp> ");
            fromUser = stdIn.readLine();

            if( !command.commandExist(fromUser) )
                System.out.println("0x001 Invalid command.");

            else if( !Utils.argumentChecker(fromUser) )
                System.out.println("0x002 Incorrect number of arguments.");

            else if(fromUser.equals("quit")){
                break;
            }

            else server.takeInput(fromUser);
        }
    }
}
