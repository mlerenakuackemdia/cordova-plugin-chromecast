package acidhax.cordova.chromecast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.MediaIntentReceiver;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import android.util.Log;
import android.R;

import android.content.Context;
import android.text.format.DateUtils;

public final class CastOptionsProvider implements OptionsProvider {

    /** The app id. */
    private static String appId;

    /**
     * Sets the app ID.
     * @param applicationId appId
     */
    public static void setAppId(String applicationId) {
        Log.i("Cast getCastOptions setAppId", applicationId);
        appId = applicationId;
    }

    @Override
    public CastOptions getCastOptions(Context context) {
        // Example showing 4 buttons: "rewind", "play/pause", "forward" and "stop casting".
        Log.i("Cast getCastOptions activity", "MainActivity");
        List<String> buttonActions = new ArrayList<>();
        buttonActions.add(MediaIntentReceiver.ACTION_SKIP_PREV);
        buttonActions.add(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK);
        buttonActions.add(MediaIntentReceiver.ACTION_SKIP_NEXT);
        buttonActions.add(MediaIntentReceiver.ACTION_STOP_CASTING);

// Showing "play/pause" and "stop casting" in the compat view of the notification.
        int[] compatButtonActionsIndices = new int[]{0, 1, 2, 3};

        // Builds a notification with the above actions. Each tap on the "rewind" and "forward" buttons skips 30 seconds.
// Tapping on the notification opens an Activity with class VideoBrowserActivity.
        NotificationOptions notificationOptions = new NotificationOptions.Builder()
                .setTargetActivityClassName(context.getPackageName() + ".MainActivity")
                .setActions(buttonActions, compatButtonActionsIndices)
                .build();

        CastMediaOptions mediaOptions = new CastMediaOptions.Builder()
                .setNotificationOptions(notificationOptions)
               // .setMediaSessionEnabled(false)
                .build();
        return new CastOptions.Builder()
                .setCastMediaOptions(mediaOptions)
                .setReceiverApplicationId(appId)
                .build();
    }
    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }
}
