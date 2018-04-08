/*
 * RequesterViewHolder
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Adapters;

import android.widget.TextView;

/**
 * Holds the view objects for the ProviderCustomArrayAdapter.
 * @author zhipeng
 * @see RequesterCustomArrayAdapter
 */

public class RequesterViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskAcceptBidAmountTextField;

    /**
     * Constructs a holder for the assigned tasks view.
     * @param statusTextField The TextView object displaying the task's status.
     * @param userNameTextField The TextView object displaying the assigned provider's username.
     * @param taskTitleTextField The TextView object displaying the task's title.
     * @param taskAcceptBidAmountTextField The TextView object displaying the accepted bid.
     */
    public RequesterViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskAcceptBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskAcceptBidAmountTextField = taskAcceptBidAmountTextField;
    }

    /**
    * Constructs a holder for the requested tasks view.
    * @param statusTextField The TextView object displaying the task's status.
    * @param taskTitleTextField The TextView object displaying the task's title.
    */
    public RequesterViewHolder(TextView statusTextField, TextView taskTitleTextField){
        this.statusTextField = statusTextField;
        this.taskTitleTextField = taskTitleTextField;
    }

    /**
     * Getter method for statusTextField.
     * @return statusTextField
     */
    public TextView getStatusTextField() {
        return statusTextField;
    }

    /**
     * Getter method for UserNameTextField
     * @return UserNameTextField
     */
    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    /**
     * Getter method for TaskTitleTextField
     * @return taskTitleTextField
     */
    public TextView getTaskTitleTextField() { return taskTitleTextField; }

    /**
     * Getter method for taskAcceptBidAmount
     * @return taskAcceptBidAmount
     */
    public TextView getTaskAcceptBidAmount() { return taskAcceptBidAmountTextField;}
}
