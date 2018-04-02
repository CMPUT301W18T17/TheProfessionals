package professional.team17.com.professional;

import android.util.Log;

/**
 * Created by ag on 2018-03-28.
 */

public class OfflineException  extends Exception{
        ConnectedState c = ConnectedState.getInstance();

        public OfflineException() {
                c.setOffline();
        }
}
