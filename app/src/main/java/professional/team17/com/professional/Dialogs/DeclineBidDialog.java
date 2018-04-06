package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Dialog to decline bid on task
 */
public class DeclineBidDialog extends DialogContent {
    public DeclineBidDialog(View view) {
        super(view);
        positive.setText("Yes");
        negative.setText( "Cancel");
        dmessage.setText("Are you sure you want to decline this bid? It will be deleted.");
        dtitle.setText( "Decline Bid");
    }
}
