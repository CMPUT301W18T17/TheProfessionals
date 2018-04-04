package professional.team17.com.professional.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import professional.team17.com.professional.ConfirmDialog;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Task;
import professional.team17.com.professional.TaskList;
import professional.team17.com.professional.TaskStatus;



/**
 * Created by ag on 2018-04-03.
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
                RequesterListProviderView vh1 = (RequesterListProviderView) holder;
                configureRequested(vh1, position);
                break;
            case 1:
                BiddedListProviderView vh2 = (BiddedListProviderView) holder;
                configureBidded(vh2, position);
                break;
            case 2:
                AssignedListProviderView vh3 = (AssignedListProviderView) holder;
                configureAssigned(vh3, position);
                break;
            default:
                break;
        }



    }

    private void configureBidded(BiddedListProviderView vh2, int position) {
        task = items.get(position);
        vh2.getStatusTextField().setText(task.getStatus());
        vh2.getUserNameTextField().setText(task.getProfileName());
        vh2.getTaskTitleTextField().setText(task.getName());
        vh2.getTaskLowBidAmount().setText(task.getBids().getLowest().getAmountAsString());
        if (task.getBids().userBidded(username)) {
            vh2.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());

        }
    }

    private void configureAssigned(AssignedListProviderView vh2, int position) {
        task = items.get(position);
        vh2.getStatus().setText(task.getStatus());
        vh2.getUserName().setText(task.getProfileName());
        vh2.getTaskTitle().setText(task.getName());
        vh2.MyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
    }

    private void configureRequested(RequesterListProviderView vh1, int position) {
        task = items.get(position);
        vh1.getStatus().setText(task.getStatus());
        vh1.getUserName().setText(task.getProfileName());
        vh1.getName().setText(task.getName());
    }


    //Returns the view type of the item at position for the purposes of view recycling.
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
