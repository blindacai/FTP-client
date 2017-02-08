import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linda on 2/3/2017.
 */
public class Utils {
    static String ip_addr;
    static int port_number;

    /*
        check if the current response is the last response
        argv: server response
        return true if it's not, false otherwise
     */
    public static Boolean notlastline(String response){
        if(!getMatch("(\\d\\d\\d)", response).find())
            return true;
        if(getMatch("(-)", response).find())
            return true;
        else
            return false;
    }

    /*
        argv: server response
        return "142,103,6,49,227,166,227" part of the passive mode response
     */
    public static String IPandPort(String response){
        Matcher matcher = getMatch("\\((.*?)\\)", response);
        if(matcher.find()){
            return matcher.group(1);
        }
        else return null;
    }

    /*
        regular expression helper function
     */
    private static Matcher getMatch(String pattern, String response){
        Pattern apattern = Pattern.compile(pattern);
        return apattern.matcher(response);
    }

    /*
    parse ip from pasv mode response
    return ip address for the second data connection
 */
    public static String getIP(String[] info){
        String ip = "";
        for(int i = 0; i < 3; i++){
            ip += info[i] + ".";
        }
        ip_addr = ip + info[3];
        return ip_addr;
    }

    /*
        parse port from pasv mode response
        return port number
     */
    public static int getPort(String[] info){
        port_number = Integer.parseInt(info[4])*256 + Integer.parseInt(info[5]);
        return port_number;
    }

    /*
        return true when the number of arguments provided by the user is correct, false otherwise
     */
    public static boolean argumentChecker(String userInput){
        String[] inputs = userInput.split(" ");
        int length = inputs.length;
        String command = inputs[0];
        boolean result = false;

        switch(command){
            case "dir":
            case "quit":
            case "features":
                result = (length == 1);
                return result;
            case "user":
            case "pw":
            case "get":
            case "cd":
                result = (length == 2);
                return result;
        }
        return result;
    }

    public static void errorMessage(String code){
        switch(code){
            case "001":
                System.out.println("Invalid command.");
                break;
            case "002":
                System.out.println("Incorrect number of arguments.");
                break;
            case "FFFC":
                System.out.println("Control connection to " + ip_addr + " on port" + port_number + " failed to open.");
            case "FFFD ":
                System.out.println("Control connection I/O error, closing control connection.");
                break;
            case "3A2 ":
                System.out.println("Data transfer connection to " + ip_addr + " on port" + port_number + " failed to open.");
            case "3A7 ":
                System.out.println("Data transfer connection I/O error, closing data connection.");
                break;
            case "FFFE  ":
                System.out.println("Input error while reading commands, terminating.");
                System.exit(0);
        }
    }

    public static void setIPandPort(String ip, int port){
        ip_addr = ip;
        port_number = port;
    }
}
