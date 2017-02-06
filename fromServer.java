import java.io.*;

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
        command.setUserinput(userInput);

        if(!command.specialCommand(command.getUserinput_command())){
            kkSocket.getout().println(command.getFTPcommand());
            printResponse();
        }
        else if(command.commandExist(command.getUserinput_command())){
            specialInput(userInput);
        }
    }


    private void specialInput(String userInput) throws IOException {
        kkSocket.getout().println(PASV);
        String serverRes = kkSocket.getin().readLine();
        String[] info = Utils.IPandPort(serverRes).split(",");

        // a second socket
        theSocket second_socket = new theSocket(getIP(info), getPort(info));
        second_socket.createSocket();

        // switch to binary mode
        kkSocket.getout().println("type I");
        printResponse();

        // send a second command
        kkSocket.getout().println(command.getFTPcommand());

        printResponse();
        if(command.getUserinput_command().equals("dir")){
            printSpecial(second_socket);
        }
        else{
            getFile(second_socket, userInput);
        }
        printResponse();
    }

    private void getFile(theSocket second_socket, String userInput) throws IOException {
        command.setUserinput(userInput);
        OutputStream oos = new FileOutputStream(new File("./" + command.getUserinput_var()));
        int reading;
        while(( reading = second_socket.getin().read() ) > -1){
            oos.write((byte) reading);
            oos.flush();
        }
        oos.close();
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
