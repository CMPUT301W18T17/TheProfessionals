package professional.team17.com.professional;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public TaskList(){
        // Nothing
    }

    public boolean hasTask(Task task){
        return this.contains(task);
    }

    public void addTask(Task task){ this.add(task); }

    public void deleteTask(Task task){
        this.remove(task);
    }

    public ArrayList<Task> getTasks() {
        return this;
    }
}
