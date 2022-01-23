import java.util.ArrayList;
import java.util.Arrays;

//In CSV file exported by an alarm, Action is a type of data. An action is a characterized string.

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
        String actionWithoutSpace = action.replaceAll("\\s","");
        boolean isReferencedAction = actionType.contains(actionWithoutSpace);
        if (isReferencedAction) {
            this.action = actionWithoutSpace ;
        }
        else {
            System.out.println(actionWithoutSpace + " is not a recognized action.");
        }
    }
    //Constructor
    Action(String action){
        setAction(action);
    }
}
