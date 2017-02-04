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

    public void setUserinput(String userinput){
        String[] inputs = userinput.split(" ");
        if(inputs.length > 1){
            this.userinput_var = inputs[1];
        }
        this.userinput_command = inputs[0].toLowerCase();
    }

    public void buildMap(){
        this.map = new HashMap<String, String>();
        map.put("user", "user");
        map.put("pw", "pass");
        map.put("quit", "quit");
        map.put("features", "feat");
        map.put("cd", "cwd");
    }

    public boolean commandNeed(){
        return this.map.containsKey(this.userinput_command);
    }

    public String getFTPcommand(){
        String temp = this.map.get(this.userinput_command).toString().toUpperCase() + ( (this.userinput_var == null)? "" : " " + this.userinput_var );
        this.userinput_var = null;
        return temp;
    }
}
