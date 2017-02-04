import java.io.IOException;

/**
 * Created by linda on 2/4/2017.
 */
public class fromServer {
    private theSocket kkSocket;
    private Command command;

    public fromServer(theSocket socket, Command command){
        this.kkSocket = socket;
        this.command = command;
    }

    public void printResponse() throws IOException {
        String fromServer;
        do{
            fromServer = kkSocket.getin().readLine();
            System.out.println(fromServer);
        }while(Utils.notlastline(fromServer));
    }

    public void takeInput(String userInput){
        if(!command.specialCommand(userInput)){
            command.setUserinput(userInput);
            kkSocket.getout().println(command.getFTPcommand());
        }
        else{

        }
    }
}
