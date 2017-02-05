import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linda on 2/4/2017.
 */
public class fromServer {
    private theSocket kkSocket;
    private Command command;

    public static final String PASV = "pasv";

    public fromServer(theSocket socket, Command command){
        this.kkSocket = socket;
        this.command = command;
    }

    public void printResponse() throws IOException {
        String serverResponse;
        do{
            serverResponse = kkSocket.getin().readLine();
            System.out.println(serverResponse);
        }while(Utils.notlastline(serverResponse));
    }

    private void printSpecial(theSocket second_socket) throws IOException {
        String serverReponse;
        while(( serverReponse = second_socket.getin().readLine() ) != null){
            System.out.println(serverReponse);
        }
    }

    public void takeInput(String userInput) throws IOException {
        if(!command.specialCommand(userInput)){
            command.setUserinput(userInput);
            if(command.commandExist(command.getUserinput_command())){
                switch(command.getUserinput_command()){
                    case "dir":
                    case "quit":
                    case "features":
                        if(userInput.split(" ").length != 1){
                            System.out.println("0x002 Incorrect number of arguments.");
                            kkSocket.getout().println();
                            break;
                        } else {
                            kkSocket.getout().println(command.getFTPcommand());
                            printResponse();
                            break;
                        }
                    case "user":
                    case "pw":
                    case "get":
                    case "cd":
                        if(userInput.split(" ").length != 2){
                            System.out.println("0x002 Incorrect number of arguments.");
                            kkSocket.getout().println();
                            break;
                        } else {
                            kkSocket.getout().println(command.getFTPcommand());
                            printResponse();
                            break;
                        }
                    default:
                        break;
                }
            } else {
                System.out.println("0x001 Invalid command.");
                kkSocket.getout().println();
            }
        }
        else if(command.commandExist(userInput)){
            specialInput(userInput);
        } else {
            System.out.println(command.commandExist(userInput));
            System.out.println("error");
        }
    }

    private void specialInput(String userInput) throws IOException {
        kkSocket.getout().println(PASV);
        String serverRes = kkSocket.getin().readLine();
        String[] info = Utils.IPandPort(serverRes).split(",");

        // a second socket
        theSocket second_socket = new theSocket(getIP(info), getPort(info));
        second_socket.createSocket();

        // send a second command
        command.setUserinput(userInput);
        kkSocket.getout().println(command.getFTPcommand());

        printResponse();
        printSpecial(second_socket);
        printResponse();
    }

    private String getIP(String[] info){
        String ip = "";
        for(int i = 0; i < 3; i++){
            ip += info[i] + ".";
        }
        return ip + info[3];
    }

    private int getPort(String[] info){
        return Integer.parseInt(info[4])*256 + Integer.parseInt(info[5]);
    }
}
