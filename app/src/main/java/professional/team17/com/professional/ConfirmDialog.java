/*
 * ConfirmDialog
 *
 * March 5, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import professional.team17.com.professional.Dialogs.CustomDialogFactory;

/**
 *
 * A dialog fragment that will will work with an activity, user must bundle in the
 * dialog title, message, and cancel/confirm button text.
 * This implements an interface that is called within the activity to see if the user
 * confirmed/cancelled the dialog interaction
 *
 * @author Allison
 */
public class ConfirmDialog extends DialogFragment {


    /**
     * This is the interface used to talk to the activity.
     * It holds the user intput in variable "confirmed"
     */
    public interface ConfirmDialogListener {
        void onFinishConfirmDialog(Boolean confirmed);
        void onFinishConfirmDialog(Boolean confirmed, String dialog);
    }


    private ConfirmDialogListener listener;


    /**
    * Implement the empty method from interface ConfirmDialogListener
    */
    public ConfirmDialog() {}


    /**
     *
     * @param context - the activity context calling the dialog
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ConfirmDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "Activity must implement the ConfirmDialogListener");
        }
    }

    /**
     * Override the onCreateView Builder
     * @param inflater - the layout being inflated
     * @param container - The view calling the fragment
     * @param savedInstanceState - the string paremeters being passed, they are as follows
     *                           title: the title to display in the fragment
     *                           cancel: the text to display in the cancel button text
     *                           confirm: the text to display in confirm button text
     *                           message: the text to display as the question
     *                              (what displays within the fragment)
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.confirmation_fragment, container, false);

        final String tag=getTag();

        //get customizable fields from xml
        Button positive = (Button) rootView.findViewById(R.id.confirm_fragment_positiveButton);
        Button negative = (Button) rootView.findViewById(R.id.confirm_fragment_negButtom);
        CustomDialogFactory customDialogFactory = new CustomDialogFactory();
        customDialogFactory.make(rootView, tag);
        //negative = do nothing
        negative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //postive = confirm dialog question/check. If type of dialog it input, enter amount
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag != null) {
                    listener.onFinishConfirmDialog(true, tag);
                }
                else {
                    listener.onFinishConfirmDialog(true);
                }
                dismiss();
            }
        });

        return rootView;
    }

}