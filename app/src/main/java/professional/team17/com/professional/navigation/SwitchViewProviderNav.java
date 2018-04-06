package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterViewListActivity;

/**
 * Button to Move from Provider Activity to switch to Requester View
 */
public class SwitchViewProviderNav extends NavButton {
    public SwitchViewProviderNav(Context con) {
        super(con);
    }

    public Intent getIntent() {
        intent = new Intent(context, RequesterViewListActivity.class);
        intent.putExtra("Status", "Requested");
        return intent;
    }
}
