import java.util.HashMap;
import java.util.Map;

/**
 * Created by linda on 2/2/2017.
 */
public class Command {
    private String userinput_command = null;
    private String userinput_var = null;
    private Map map;

    public Command(){
        this.buildMap();
    }

    /*
        parse user input to two part: command eg: user, pw, dir
                                      variable eg: username, filename
     */
    public void setUserinput(String userinput){
        String[] inputs = userinput.split(" ");
        if(inputs.length > 1){
            this.userinput_var = inputs[1];
        }
        this.userinput_command = inputs[0];
    }

    /*
        map user input to ftp command
     */
    public void buildMap(){
        this.map = new HashMap<String, String>();
        map.put("user", "user");
        map.put("pw", "pass");
        map.put("quit", "quit");
        map.put("features", "feat");
        map.put("cd", "cwd");
        map.put("dir", "list");
        map.put("get", "retr");
    }

    /*
        check whether command needs extra data connection; return true if it does, otherwise false
     */
    public boolean specialCommand(String userinput){
        return (userinput.equals("get")) || (userinput.equals("dir"));
    }

    /*
        check whether user input belongs to a valid command; return true if it does, otherwise false
     */
    public boolean commandExist(String userinput){
        return this.map.containsKey(userinput);
    }

    /*
        return ftp command
     */
    public String getFTPcommand(){
        String temp = this.map.get(this.userinput_command).toString().toUpperCase() + ( (this.userinput_var == null)? ""
                : " " + this.userinput_var );

        this.userinput_var = null;
        return temp;
    }

    public String getUserinput_command(){
        return this.userinput_command;
    }

    public String getUserinput_var(){
        return this.userinput_var;
    }
}
