package caprica6.fblandroidhackathon.org.caprica6;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "wheJQaJoSCeUw9URNObUoBr5Jsj7OT2uRVF4fOmB", "BdrvtqSi81saYX6wlSkJBBDlbF3euDErmafHpqPm");
    }


}
