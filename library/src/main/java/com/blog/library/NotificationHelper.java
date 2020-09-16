package com.blog.library;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2018-04-07 16:49
 */
public class NotificationHelper extends ContextWrapper {

    private static final int NOTIFICATION_ID = 0;
    private static String CHANNEL_ID = "dxy_app_update";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, (getString(R.string.notify_title)), NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription(getString(R.string.android_auto_update_notify_ticker));
            mChannel.enableLights(true); //Whether to display a small red dot in the upper right corner of the desktop icon
            getManager().createNotificationChannel(mChannel);
        }
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {

        Intent myIntent = new Intent(this, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = getNofity(content)
                .setContentIntent(pendingIntent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }


    public void updateProgress(int progress) {


        String text = this.getString(R.string.android_auto_update_download_progress, progress);

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = getNofity(text)
                .setProgress(100, progress, false)
                .setContentIntent(pendingintent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }

    private NotificationCompat.Builder getNofity(String text) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setTicker(getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle(getString(R.string.android_auto_update_content))
                .setContentText(text)
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }

    public void cancel() {
        getManager().cancel(NOTIFICATION_ID);
    }


    private int getSmallIcon() {
        // Set the icon of nofication directly read the icon of the millet push configuration
        int icon = getResources().getIdentifier("ic_launcher", "mipmap", getPackageName());
        if (icon == 0) {
            icon = getApplicationInfo().icon;
        }

        return icon;
    }

    private Bitmap getLargeIcon() {
        int bigIcon = getResources().getIdentifier("ic_launcher", "mipmap", getPackageName());
        if (bigIcon != 0) {
            return BitmapFactory.decodeResource(getResources(), bigIcon);
        }
        return null;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
