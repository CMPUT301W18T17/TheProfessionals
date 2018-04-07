package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Dialog to decline bid on task
 */
public class DeletePhotoDialog extends DialogContent {
    public DeletePhotoDialog(View view) {
        super(view);
        positive.setText("Yes");
        negative.setText( "Cancel");
        dmessage.setText("Are you sure you want to remove this photo?");
        dtitle.setText( "Remove Photo");
    }
}
