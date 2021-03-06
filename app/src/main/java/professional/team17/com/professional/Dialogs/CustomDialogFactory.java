package professional.team17.com.professional.Dialogs;

import android.app.DialogFragment;
import android.view.View;

import professional.team17.com.professional.R;


/**
 * Factory to build the Dialogs and allow reuse
 */
public class CustomDialogFactory {

    public DialogContent make(View view, String type) {
            switch(type) {
                case "Sync":
                    return new SyncDialog(view);
                case "Cancel Bid":
                    return new CancelBidDialog(view);
                case "Accept Bid":
                    return new AcceptBidDialog(view);
                case "Delete Bid":
                    return new DeleteBidDialog(view);
                case "Set Done":
                    return new SetDoneDialog(view);
                case "Set Review":
                    return new SetReviewDialog(view);
                case "Delete Task":
                    return new DeleteTaskDialog(view);
                case "Decline Bid":
                    return new DeclineBidDialog(view);
                case "Set Requested":
                    return new SetRequestedDialog(view);
                case "Delete Photo":
                    return new DeletePhotoDialog(view);
            }
            return null;
        }

}
