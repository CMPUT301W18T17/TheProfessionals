package professional.team17.com.professional.Dialogs;

import android.view.View;

/**
 * Created by ag on 2018-04-03.
 */

public class AcceptBidDialog extends DialogContent {
    public AcceptBidDialog(View view) {
        super(view);
        dmessage.setText("Are you sure you want to accept this bid? This will delete all other bids.");
        dtitle.setText("Accept Bid");
    }
}
