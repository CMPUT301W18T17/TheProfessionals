package professional.team17.com.professional.Dialogs;

import android.view.View;



/**
 * Dialog to delete task
 */
public class DeleteTaskDialog extends DialogContent {
    public DeleteTaskDialog(View view) {
        super(view);
        dmessage.setText("Are you sure you want to delete this task?");
        dtitle.setText("Delete Task");
    }
}
