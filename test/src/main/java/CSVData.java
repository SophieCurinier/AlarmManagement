import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class contains all type of data in CSV file exported by alarm.

public class CSVData {
    //Attribute
    private Date startDateTime;
    private Date endDateTime;
    private String description ;
    private String recipient ;
    private Action action ;

    //Getters
    public Date getStartDateTime() { return startDateTime; }
    public Date getEndDateTime() {
        return endDateTime;
    }
    public String getDescription() {
        return description;
    }
    public String getRecipient() { return recipient;}
    public Action getAction() { return action; }

    //Setters
    public void setStartDate(String startDateTime, SimpleDateFormat formatter) {
        try {
            this.startDateTime = formatter.parse(startDateTime);
        } catch (ParseException e){
            System.out.println(startDateTime + " cannot be formatted. Chek it");
        }
    }
    public void setEndDate(String endDateTime, SimpleDateFormat formatter) {
        try {
            this.endDateTime = formatter.parse(endDateTime);
        } catch (ParseException e){
            System.out.println(endDateTime + " cannot be formatted. Chek it");
        }
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public void setAction(Action action) {
        this.action = action;
    }

    //Constructor
    CSVData(String startDateTime, String endDateTime, String description, String recipient, String action, SimpleDateFormat dateTimeFormatter) {
        setStartDate(startDateTime,dateTimeFormatter);
        setEndDate(endDateTime,dateTimeFormatter);
        setDescription(description);
        setRecipient(recipient);
        setAction(new Action(action));
    }
}
