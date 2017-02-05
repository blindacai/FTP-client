import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linda on 2/3/2017.
 */
public class Utils {

    public static Boolean notlastline(String response){
        if(!getMatch("(\\d\\d\\d)", response).find())
            return true;
        if(getMatch("(-)", response).find())
            return true;
        else
            return false;
    }

    public static String IPandPort(String response){
        Matcher matcher = getMatch("\\((.*?)\\)", response);
        if(matcher.find()){
            return matcher.group(1);
        }
        else return null;
    }

    private static Matcher getMatch(String pattern, String response){
        Pattern apattern = Pattern.compile(pattern);
        return apattern.matcher(response);
    }
}
