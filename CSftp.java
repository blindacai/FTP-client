
import java.lang.System;
import java.io.IOException;
import java.io.*;
import java.net.*;

//
// This is an implementation of a simplified version of a command 
// line ftp client. The program always takes two arguments
//


public class CSftp
{
    // test comment
	static final int MAX_LEN = 255;
    static final int ARG_CNT = 2;
    static final int ARG_CNT_DEFAULT_PORT = 1;

    public static void main(String [] args) {
        int portNumber = 21;
        byte cmdString[] = new byte[MAX_LEN];
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

	    /*
	    try {
	        Socket ftpSocket = new Socket(args[0], portNumber);
        } catch (IOException e){
	        System.err.println("error" + e);
        }
        */

	    try {
	        for (int len = 1; len > 0;) {
		        System.out.print("csftp> ");
		        len = System.in.read(cmdString);
		        cmdAsString = new String(cmdString);
		        if (len <= 0) break;
    		    // Start processing the command here.
                System.out.println("cmdString: " + cmdString);
                System.out.println("cmdAsString: " + cmdAsString);
                System.out.println("len: " + len);

                System.out.println("900 Invalid command.");
            }


    	} catch (IOException exception) {
    	    System.err.println("998 Input error while reading commands, terminating.");
    	}
    }
}
