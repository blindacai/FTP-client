import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by linda on 2/2/2017.
 */
public class CSftp {
    public static void main(String [] args) throws IOException {
        String hostname = null;
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
            handleException(hostname, port);
        }catch(ConnectException e){
            handleException(hostname, port);
        }


        Command command = new Command();
        fromServer server = new fromServer(kkSocket, command);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  // accept user input

        server.printResponse();
        String fromUser = "";
        while(true){
            System.out.print("csftp> ");

            try{
                fromUser = stdIn.readLine();
            }catch(IOException e){
                Utils.errorMessage("FFFE");
            }

            if( !command.commandExist(fromUser) )
                Utils.errorMessage("001");

            else if( !Utils.argumentChecker(fromUser) )
                Utils.errorMessage("002");

            else if(fromUser.equals("quit")){
                break;
            }

            else server.takeInput(fromUser);
        }
    }

    public static void handleException(String hostname, int port){
        System.out.println("0xFFFC Control connection to " + hostname + " on port " + port + " failed to open");
        System.exit(0);
    }
}
