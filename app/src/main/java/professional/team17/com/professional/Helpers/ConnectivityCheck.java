package professional.team17.com.professional.Helpers;


import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


/**
 *
 * A utility class that checks for a connection via opening a socket
 * If it find a connection, the ConnectedState singleton is set to Online,
 * If no connection, the  ConnectedState singleton is set to offline
 * This method was sourced from
 * https://stackoverflow.com/questions/44918248/android-internet-connectivity-check-better-method
 * by Rohith Krishnan on Jul 7 '17 at 10:06
 * @author Allison
 * @see ConnectedState
 */
public class ConnectivityCheck {

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