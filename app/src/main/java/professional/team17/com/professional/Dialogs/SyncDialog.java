package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Dialog to notify user of sync errors
 */
public class SyncDialog extends DialogContent {
    public SyncDialog(View view) {
        super(view);
        dmessage.setText("We were unable to sync all your edits as " +
                "users have placed bids on your tasks." +
                "Click yes to see these bids");
        dtitle.setText("Unable to Sync");
    }
}
