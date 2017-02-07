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

    /*
        print server response
        deal with multi-line response
     */
    public void printResponse() throws IOException {
        String serverResponse = "";
        do{
            try{
                serverResponse = kkSocket.getin().readLine();
            }catch(IOException e){
                System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                kkSocket.getKkSocket().close();
                System.exit(0);
            }
            System.out.println("<-- " + serverResponse);
        }while(Utils.notlastline(serverResponse));
    }

    /*
        print server response of command "dir"
     */
    private void printSpecial(theSocket second_socket) throws IOException {
        String serverReponse;
        while(( serverReponse = second_socket.getin().readLine() ) != null){
            System.out.println(serverReponse);
        }
    }

    /*
        feed user input to ftp server
     */
    public void takeInput(String userInput) throws IOException {
        command.setUserinput(userInput);

        String userinput_command = command.getUserinput_command();
        String userinput_var = command.getUserinput_var();
        String ftp_command = command.getFTPcommand();

        System.out.println("--> " + userinput_command.toUpperCase() + (userinput_var != null? " " + userinput_var : ""));

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
        try{
            second_socket.createSocket();
        }catch(IOException e){
            System.out.println("0x3A2 Data transfer connection to " + getIP(info) + " " + getPort(info) + " failed to open.");
            System.exit(0);
        }

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
         //oos = null;
        try{
            OutputStream oos = new FileOutputStream(new File("./" + command.getUserinput_var()));
            byte[] buf = new byte[10];
            int offset = 0;
            while (offset > -1) {
                oos.write(buf, 0, offset);
                oos.flush();
                try{
                offset = second_socket.getinputstream().read(buf, 0, buf.length);
                }catch(IOException e){
                    System.out.println("0x3A7 Data transfer connection I/O error, closing data connection.");
                    second_socket.getKkSocket().close();
                    break;
                }
            }
            oos.close();
        }catch(FileNotFoundException e){
            System.out.println("0x38E Access to local file " + command.getUserinput_var() + " denied");
        }
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
