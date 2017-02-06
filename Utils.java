import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linda on 2/3/2017.
 */
public class Utils {

    /*
        check if the current response is the last response
        argv: server response
        return true if it's not, false if it is
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
        return "142,103,6,49,227,166 of 227" part of passive mode response
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
}
