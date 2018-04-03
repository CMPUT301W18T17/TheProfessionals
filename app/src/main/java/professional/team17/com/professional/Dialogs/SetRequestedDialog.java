package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Created by ag on 2018-04-03.
 */

public class SetRequestedDialog extends DialogContent {
    public SetRequestedDialog(View view) {
        super(view);
        dmessage.setText("Are you sure you want to set the status to Requested? This will" +
                "reopen bidding.");
        dtitle.setText("Set Status To Requested");
    }
}
