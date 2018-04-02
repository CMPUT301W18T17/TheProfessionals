package professional.team17.com.professional;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by ag on 2018-03-29.
 */

public class ConnectivityCheck {

    private Context _context;

    public static class isOnline extends AsyncTask<Void, Void, Void> {
        ConnectedState c = ConnectedState.getInstance();
        protected Void doInBackground(Void... voids){
            try {
                int timeoutMs = 400;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
                sock.connect(sockaddr, timeoutMs);
                sock.close();
                c.setOnline();
            } catch (IOException e) {
                c.setOffline();
            }

            return null;
        }
    }
}