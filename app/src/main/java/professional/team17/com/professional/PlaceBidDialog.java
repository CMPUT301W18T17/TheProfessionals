package professional.team17.com.professional;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * Created by ag on 2018-03-04.need to add listender to buttons, check string input, and send it back
 */

public class PlaceBidDialog extends DialogFragment {


    private EditText mEditText;

    public PlaceBidDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PlaceBidDialog newInstance(String title) {
        PlaceBidDialog frag = new PlaceBidDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_bid_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.place_bid_fragment_bid_input);
        // Fetch arguments from bundle and set title
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
     * @param editText - The edit text field whose input will strip trailing white spaces
     * @return - This will return a cleaned up string
     */
    public String cleanSpace(EditText editText){
        String string = editText.getText().toString();
        String input = string.trim();
        return input;
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

