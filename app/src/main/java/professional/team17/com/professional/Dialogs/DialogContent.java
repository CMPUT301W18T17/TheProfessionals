package professional.team17.com.professional.Dialogs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import professional.team17.com.professional.R;

/**
 * Class that helps build the dialogs and allows for
 * reuse of them
 */
public abstract class DialogContent {
    View view;
    Button positive;
    Button negative;
    TextView dmessage;
    TextView dtitle;

    public DialogContent(View view){
        this.view = view;
        positive = (Button) view.findViewById(R.id.confirm_fragment_positiveButton);
        negative = (Button) view.findViewById(R.id.confirm_fragment_negButtom);
        dmessage = (TextView) view.findViewById(R.id.confirm_fragment_text);
        dtitle = (TextView) view.findViewById(R.id.confirm_fragment_title);
    }
}
