package professional.team17.com.professional;


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

/**
 * Created by ag on 2018-03-04.need to add listender to buttons, check string input, and send it back
 */

public class PlaceBidDialog extends DialogFragment {


    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private EditNameDialogListener listener;


    public PlaceBidDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (EditNameDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.place_bid_fragment, container, false);
        getDialog().setTitle("Place Bid");


        Button dismiss = (Button) rootView.findViewById(R.id.place_bid_fragment_cancelButton);
        Button add = (Button) rootView.findViewById(R.id.place_bid_fragment_addButton);

        final EditText bidInput = (EditText) rootView.findViewById(R.id.place_bid_fragment_bid_input);
        final TextView warning = (TextView) rootView.findViewById(R.id.place_bid_fragment_warning);




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
                    listener.onFinishEditDialog(bidInput.getText().toString());
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

