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

/**
 * Created by ag on 2018-03-04.need to add listender to buttons, check string input, and send it back
 */

public class ConfirmDialog extends DialogFragment {

    public interface ConfirmDialogListener {
        void onFinishConfirmDialog(Boolean confirmed);
    }

    private ConfirmDialogListener listener;


    public ConfirmDialog() {
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (ConfirmDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement ConfirmDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.confirmation_fragment, container, false);
        Bundle args = getArguments();

        //get args activity passed into dialog
        String title = args.getString("title");
        String cancelButton = args.getString("cancel");
        String confirmButton = args.getString("confirm");
        String message = args.getString("message");

        //get customizable fields from xml
        Button positive = (Button) rootView.findViewById(R.id.confirm_fragment_positiveButton);
        Button negative = (Button) rootView.findViewById(R.id.confirm_fragment_negButtom);
        TextView dmessage = (TextView) rootView.findViewById(R.id.confirm_fragment_text);



        //set text for each customizable field
        positive.setText(confirmButton);
        negative.setText(cancelButton);
        dmessage.setText(message);
        dtitle.setText(title);

        //negative = do nothing
        negative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //postive = confirm dialog question/check
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener.onFinishConfirmDialog(true);
                    dismiss();
                }
        });


        return rootView;
    }





}

