import java.util.ArrayList;
import java.util.Arrays;

public class Action {
    //Attribute
    private String action ;
    private static ArrayList<String> actionType = new ArrayList<>(Arrays.asList("Ack","NotAck","AutoAck"));

    //Getter
    public String getAction() {
        return action;
    }
    //Setters
    public void setAction(String action){
        if (actionType.contains(action.replaceAll("\\s",""))) {
            this.action = action.replaceAll("\\s","") ;
        }
        else {
            System.out.println(action + " is not a recognized action.");
        }
    }
    //Constructor
    Action(String action){
        setAction(action);
    }
}
