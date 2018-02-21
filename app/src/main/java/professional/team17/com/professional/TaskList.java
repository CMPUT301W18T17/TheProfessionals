package professional.team17.com.professional;

import java.util.ArrayList;

/**
 * Created by Hailan on 2018-02-20.
 */

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public TaskList(){
        // Nothing
    }

    public boolean hasTask(Task task){
        return tasks.contains(task);
    }

    public void addReview(Task task){ tasks.add(task); }

    public void deleteReview(Bid bid){
        tasks.remove(bid);
    }
}
