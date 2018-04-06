
package professional.team17.com.professional;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * An empty activity which displays a splash screen and redirects to the main activity.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
        startActivity(intent);

        finish();
    }
}