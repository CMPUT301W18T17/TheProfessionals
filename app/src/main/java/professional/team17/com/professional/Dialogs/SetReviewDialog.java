package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Dialog to add a review
 */
public class SetReviewDialog extends DialogContent {
    public SetReviewDialog(View view) {
        super(view);
        negative.setText( "No");
        dmessage.setText("Would you like to review your provider?");
        dtitle.setText("Review Provider");
    }
}
