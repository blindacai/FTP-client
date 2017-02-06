import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



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

    /*
        print server response
        deal with multi-line response
     */
    public void printResponse() throws IOException {
        String serverResponse;
        do{
            serverResponse = kkSocket.getin().readLine();
            System.out.println("<--" + serverResponse);
        }while(Utils.notlastline(serverResponse));
    }

    /*
        print server response of command "dir"
     */
    private void printSpecial(theSocket second_socket) throws IOException {
        String serverReponse;
        while(( serverReponse = second_socket.getin().readLine() ) != null){
            System.out.println("<--" + serverReponse);
        }
    }

    /*
        feed user input to ftp server
     */
    public void takeInput(String userInput) throws IOException {
        command.setUserinput(userInput);
        String userinput_command = command.getUserinput_command();
        String ftp_command = command.getFTPcommand();
        System.out.println("-->" + ftp_command.toUpperCase());

        if(!command.specialCommand(userinput_command)){
            kkSocket.getout().println(ftp_command);
            printResponse();
        }
        else specialInput(userInput);
    }


    /*
        when user input is "dir" or "get"
     */
    private void specialInput(String userInput) throws IOException {
        command.setUserinput(userInput);
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

    /*
    transfer file to local machine
    */
    private void getFile(theSocket second_socket, String userInput) throws IOException {
        command.setUserinput(userInput);
        OutputStream oos = new FileOutputStream(new File("./" + command.getUserinput_var()));
        byte[] buf = new byte[10];
        int offset = 0;
        while ((offset = second_socket.getinputstream().read(buf, 0, buf.length)) > 0) {
            oos.write(buf, 0, offset);
            oos.flush();
        }
        oos.close();
    }

    /*
        parse ip from pasv mode response
        return ip address for the second data connection
     */
    private String getIP(String[] info){
        String ip = "";
        for(int i = 0; i < 3; i++){
            ip += info[i] + ".";
        }
        return ip + info[3];
    }

    /*
        parse port from pasv mode response
        return port number
     */
    private int getPort(String[] info){
        return Integer.parseInt(info[4])*256 + Integer.parseInt(info[5]);
    }

    public Command getCommand(){
        return this.command;
    }
}
