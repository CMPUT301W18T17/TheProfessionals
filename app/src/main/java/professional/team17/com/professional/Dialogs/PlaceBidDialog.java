/*
 * PlaceBidDialog
 *
 * March 5, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional.Dialogs;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import professional.team17.com.professional.R;

/**
 *
 * A dialog fragment that will will work with an taskprovideractivity
 * This allows the user to edit their bid or add a bid.
 * This implements an interface that is called within the activity to see if the user
 * added/edited the bid, or cancelled the dialog
 *
 * @author Allison
 */
public class PlaceBidDialog extends DialogFragment {


    /**
     * This is the interface used to talk to the activity.
     * It holds the user intput in variable "confirmed"
     */
    public interface PlaceBidDialogListener {
        void onFinishPlaceBidDialog(String inputText);
    }

    private PlaceBidDialogListener listener;

    /*
     * Implement the empty method from interface ConfirmDialogListener
     */
    public PlaceBidDialog() {
    }


    /**
     *
     * @param context - the activity context calling the dialog
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PlaceBidDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "Activity must implement the PlaceBidDialogListener");
        }
    }


    /**
     * Override the onCreateView Builder
     * @param inflater - the layout being inflated
     * @param container - The view calling the fragment
     * @param savedInstanceState - the string paremeters being passed, they are as follows
     *                           title: the title to display in the fragment
     *                           amount: the string representing the amount to be placed into the amount field
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.place_bid_fragment, container, false);
        getDialog().setTitle("Place Bid");

        Bundle args = getArguments();

        //get args activity passed into dialog
        String title = args.getString("title");
        String amount = args.getString("amount");

        final TextView fieldtitle = (TextView) rootView.findViewById(R.id.place_bid_fragment_title);
        final EditText bidInput = (EditText) rootView.findViewById(R.id.place_bid_fragment_bid_input);
        final TextView warning = (TextView) rootView.findViewById(R.id.place_bid_fragment_warning);

        Button dismiss = (Button) rootView.findViewById(R.id.place_bid_fragment_cancelButton);
        Button add = (Button) rootView.findViewById(R.id.place_bid_fragment_addButton);

        fieldtitle.setText(title);
        bidInput.setText(amount);


        //remove warning text if shown
        bidInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warning.isShown()){
                    warning.setVisibility(rootView.GONE);
                }
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //check for empty input, otherwise save
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(bidInput)==false){
                    listener.onFinishPlaceBidDialog(bidInput.getText().toString());
                    dismiss();
                }
                else {
                    if (!warning.isShown()) {
                        warning.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return rootView;
    }




    /**
     *
     * @param editText - The edittext field whose input will be checked to see if it is empty
     * @return - Will return true if the edittext field is empty, or false if full.
     */
    public boolean isEmpty(EditText editText) {
        String text = editText.getText().toString();
        if (text.length() == 0) {
            return true;
        }
        return false;
    }



    /**
     *
     * @param editText - The edit text field will be round to two decimal places
     * @return Will return the rounded value, or null (if text cannot be converted)
     */
    public String round(EditText editText){
        String text = editText.getText().toString();
        if (catchDec(text)) {
            BigDecimal amount = new BigDecimal(text);
            String rounded = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
            return rounded;
        }
        return null;
    }
    /**
     *
     * @param text - The text field to be checked if it can be parsed into decimal places
     * @return Will return true if it can be converted, otherwise false;
     * */
    private boolean catchDec(String text){
        boolean valid = true;
        for(int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c!='.'){
                if (!Character.isDigit(c)){
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

}