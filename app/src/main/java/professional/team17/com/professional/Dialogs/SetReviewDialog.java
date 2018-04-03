package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Created by ag on 2018-04-03.
 */

public class SetReviewDialog extends DialogContent {
    public SetReviewDialog(View view) {
        super(view);
        positive.setText("Yes");
        negative.setText( "No");
        dmessage.setText("Would you like to review your provider?");
        dtitle.setText("Review Provider");
    }
}
