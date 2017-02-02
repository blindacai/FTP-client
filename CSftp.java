import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//
// This is an implementation of a simplified version of a command 
// line ftp client. The program always takes two arguments
//


public class CSftp
{
    // constant
	static final int MAX_LEN = 255;
    static final int ARG_CNT = 2;
    static final int ARG_CNT_DEFAULT_PORT = 1;

    public static void main(String [] args) {
        int portNumber = 21;
        byte cmdString[] = new byte[MAX_LEN];   // size of char is one byte
        String cmdAsString;

	    // Get command line arguments and connected to FTP
	    // If the arguments are invalid or there aren't enough of them
        // then exit.
	    if ((args.length > ARG_CNT) || (args.length == 0)) {
    	    System.out.print("Usage: cmd ServerAddress ServerPort\n");
	        return;
	    }

	    if (args.length == ARG_CNT){
	        int temp = Integer.parseInt(args[1]);
	        portNumber = temp;
        }

        System.out.println("host: " + args[0]);
	    System.out.println("port: " + portNumber);



        String hostName = args[0];

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));  // accept user input
            String fromServer;
            String fromUser;

            while((fromServer = in.readLine()) != null){
                System.out.println("this is in" + fromServer);
                fromUser = stdIn.readLine();
                if(fromUser.equals("user")){
                    out.println("user anonymous");    // the line to feed user input to ftp server through socket
                }
            }

            /*
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
            */
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

/*
	    try {
            // infinite loop
            // when user types in a + enter, loop will run to the end twice before
            // it keeps running the loop or the program(when there is nothing for read() to read)
            // enter means feed a new line, ASCII code: 10
            // System.in.read() returns the byte it read, one at a time
            // System.in.read(with arg) returns the number of byte it read
	        for (int len = 1; len > 0;) {
		        System.out.print("csftp> ");

		        len = System.in.read(cmdString);
                //int test = System.in.read();
		        //cmdAsString = new String(cmdString);
                //System.out.println("cmdAsString: " + cmdAsString);

		        if (len <= 0) break;
    		    // Start processing the command here.
                System.out.println("cmdString: " + cmdString[0]);
                System.out.println("len: " + len);
                //System.out.println("test: " + test);

                System.out.println("900 Invalid command.");
            }


    	} catch (IOException exception) {
    	    System.err.println("998 Input error while reading commands, terminating.");
    	}

    	*/
    }

    public static void feedsocket(String userinput){

    }
}