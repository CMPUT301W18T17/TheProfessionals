package professional.team17.com.professional.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import professional.team17.com.professional.R;
import professional.team17.com.professional.Entity.Task;
import professional.team17.com.professional.Entity.TaskList;
import professional.team17.com.professional.Helpers.TaskStatus;


/**
 * This is the adapter to help with provider recycler lists This was implemented
 * as the listview was quite slow.
 */
public class ProviderListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TaskList items;
    private TaskStatus taskStatus;
    public Task task;
    String username;

    public ProviderListViewAdapter(TaskList items) {
        taskStatus = new TaskStatus();
        this.items = items;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SharedPreferences sharedpreferences = parent.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
        View v =null;
        switch (viewType) {

            case 0:
                v = inflater.inflate(R.layout.provider_requested_row, parent, false);
                viewHolder = new RequesterListProviderView(v);
                break;
            case 1:
                v = inflater.inflate(R.layout.provider_bidded_row, parent, false);
                viewHolder = new BiddedListProviderView(v);
                break;
            case 2:
                v = inflater.inflate(R.layout.provider_assigned_row, parent, false);
                viewHolder = new AssignedListProviderView(v);
                break;
            default:
                viewHolder = null;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int p = position;

        switch (holder.getItemViewType()) {
            case 0:
                RequesterListProviderView v = (RequesterListProviderView) holder;
                configureRequested(v, position);
                break;
            case 1:
                BiddedListProviderView v2 = (BiddedListProviderView) holder;
                configureBidded(v2, position);
                break;
            case 2:
                AssignedListProviderView v3 = (AssignedListProviderView) holder;
                configureAssigned(v3, position);
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param v - They row view being set
     * @param position - the index of the task in the tasklist
     */
    private void configureBidded(BiddedListProviderView v, int position) {
        task = items.get(position);
        v.getStatusTextField().setText(task.getStatus());
        v.getUserNameTextField().setText(task.getProfileName());
        v.getTaskTitleTextField().setText(task.getName());
        v.getTaskLowBidAmount().setText(task.getBids().getLowest().getAmountAsString());
        if (task.getBids().userBidded(username)) {
            v.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
        }
    }

    /**
     *
     * @param v - They row view being set
     * @param position - the index of the task in the tasklist
     */
    private void configureAssigned(AssignedListProviderView v, int position) {
        task = items.get(position);
        v.getStatus().setText(task.getStatus());
        v.getUserName().setText(task.getProfileName());
        v.getTaskTitle().setText(task.getName());
        v.MyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
    }

    /**
     *
     * @param v - They row view being set
     * @param position - the index of the task in the tasklist
     */
    private void configureRequested(RequesterListProviderView v, int position) {
        task = items.get(position);
        v.getStatus().setText(task.getStatus());
        v.getUserName().setText(task.getProfileName());
        v.getName().setText(task.getName());
    }


    /**
     *
     * @param position - the position being looked at in the recycler
     * @return - The type of view
     *          - 0 = requested
     *          - 1 = Bidded
     *          - 2 = Assigned
     *          - 3 = Done
     */
    @Override
    public int getItemViewType(int position) {
        String status = items.get(position).getStatus();
        switch (status) {
            case "Requested":
                return 0;
            case "Bidded":
                return 1;
            case "Assigned":
                return 2;
            case "Done":
                return 2;
            default:
               return -1;
        }

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

}
