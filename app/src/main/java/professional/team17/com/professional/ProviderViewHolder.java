/*
 * ProviderViewHolder
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Holds the view objects for the ProviderCustomArrayAdapter.
 * @author zhipeng
 * @see ProviderCustomArrayAdapter
 */

public class ProviderViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskLowBidAmountTextField;
    TextView taskMyBidAmountTextField;

    /**
     * Constructs a holder object for the bidded tasks view
     * @param statusTextField The TextView that displays a task's status
     * @param userNameTextField The TextView that displays the task requester's name
     * @param taskTitleTextField The TextView that displays the task's name
     * @param taskLowBidAmountTextField The TextView that displays the lowest bid
     * @param taskMyBidAmountTextField The TextView that displays the user's bid
     */
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskLowBidAmountTextField, TextView taskMyBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskLowBidAmountTextField = taskLowBidAmountTextField;
        this.taskMyBidAmountTextField = taskMyBidAmountTextField;
    }

    /**
     * Constructs a holder object for the requested tasks view
     * @param statusTextField The TextView that displays a task's status
     * @param userNameTextField The TextView that displays the task requester's name
     * @param taskTitleTextField The TextView that displays the task's name
     */
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
    }

    /**
     * Constructs a holder object for the bidded tasks view
     * @param statusTextField The TextView that displays a task's status
     * @param userNameTextField The TextView that displays the task requester's name
     * @param taskTitleTextField The TextView that displays the task's name
     * @param taskMyBidAmountTextField The TextView that displays the user's bid
     */
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskMyBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskMyBidAmountTextField = taskMyBidAmountTextField;
    }


    /**
     * Getter method for statusTextField
     * @return statusTextField
     */
    public TextView getStatusTextField() {
        return statusTextField;
    }

    /**
     * Getter method for usernameTextField
     * @return userNameTextField
     */
    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    /**
     * Getter method for taskTitleTextField
     * @return taskTitleTextField
     */
    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }

    /**
     * Getter method for taskLowBidAmountTextField
     * @return taskLowBidAmountTextField
     */
    public TextView getTaskLowBidAmount() { return taskLowBidAmountTextField;}

    /**
     * Getter method for taskMyBidAmountTextField
     * @return taskMyBidAmountTextField
     */
    public TextView getTaskMyBidAmount() { return taskMyBidAmountTextField;}
}
