import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVData {

    private Date startDateTime;
    private Date endDateTime;
    private String description ;
    private String recipient ;
    private Action action ;

    //Getters
    public Date getStartDateTime() {
        return startDateTime;
    }
    public Date getEndDateTime() {
        return endDateTime;
    }
    public String getDescription() {
        return description;
    }
    public String getRecipient() {
        return recipient;
    }
    public Action getAction() {
        return action;
    }
    //Setters
    public void setStartDate(String startDate, SimpleDateFormat formatter) throws ParseException {
        this.startDateTime = formatter.parse(startDate);
    }
    public void setEndDate(String endDate, SimpleDateFormat formatter) throws ParseException {
        this.endDateTime = formatter.parse(endDate);
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
    CSVData(String startDateTime, String endDateTime, String description, String recipient, String action, SimpleDateFormat dateTimeFormatter) throws ParseException {
        setStartDate(startDateTime,dateTimeFormatter);
        setEndDate(endDateTime,dateTimeFormatter);
        setDescription(description);
        setRecipient(recipient);
        setAction(new Action(action));
    }
}
