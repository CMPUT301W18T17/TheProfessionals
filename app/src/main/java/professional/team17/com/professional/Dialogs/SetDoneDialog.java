package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Created by ag on 2018-04-03.
 */

public class SetDoneDialog extends DialogContent {
    public SetDoneDialog(View view) {
        super(view);
        dmessage.setText("Are you sure you want to set status to Done?");
        dtitle.setText("Set Status to Done");
    }
}
