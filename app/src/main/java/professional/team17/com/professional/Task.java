package professional.team17.com.professional;

import java.util.Date;

public class Task {
    private String taskRequester; //person who requested the task
    private String title;
    private String description;
    private String Location;
    private Date date;
    //private String profilePic;


    public Task(String taskRequester, String title, String description, String location) {
        this.taskRequester = taskRequester;
        this.title = title;
        this.description = description;
        Location = location;
        this.date = new Date();
    }

    public String getTaskRequester() {
        return taskRequester;
    }

    public void setTaskRequester(String taskRequester) {
        this.taskRequester = taskRequester;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Date getDate() {
        return date;
    }
}
