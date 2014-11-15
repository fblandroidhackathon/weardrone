package caprica6.fblandroidhackathon.org.caprica6;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,
                getString(R.string.parse_app_id), getString(R.string.parse_client_id));
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
    }


}
