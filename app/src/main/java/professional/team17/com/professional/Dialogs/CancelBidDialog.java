package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Dialog to cancel bid
 */
public class CancelBidDialog extends DialogContent {
    public CancelBidDialog(View view) {
        super(view);
        dmessage.setText("Are you sure you want to delete?");
        dtitle.setText("Cancel Bid");
    }
}
