package professional.team17.com.professional;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public TaskList(){
        // Nothing
    }

    public boolean hasTask(Task task){
        return tasks.contains(task);
    }

    public void addTask(Task task){ tasks.add(task); }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
