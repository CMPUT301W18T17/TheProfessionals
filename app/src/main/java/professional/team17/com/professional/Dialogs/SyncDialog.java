package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Created by ag on 2018-04-03.
 */

public class SyncDialog extends DialogContent {
    public SyncDialog(View view) {
        super(view);
        positive.setText("Yes");
        negative.setText("Cancel");
        dmessage.setText("We were unable to sync all your edits as " +
                "users have placed bids on your tasks." +
                "Click yes to see these bids");
        dtitle.setText("Unable to Sync");
    }
}
