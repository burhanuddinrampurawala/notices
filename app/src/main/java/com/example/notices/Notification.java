package com.example.notices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by admin on 06/10/2016.
 */

public class Notification extends FirebaseMessagingService {

    SessionManager s = new SessionManager(getApplicationContext());
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String description = remoteMessage.getNotification().getBody();
        Intent i;

        if (s.isLoggedIn()||s.isadmin()){
             i = new Intent(getApplicationContext(), NoticeList.class);
        }
        else {
             i = new Intent(getApplicationContext(), login.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent p = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder n = new NotificationCompat.Builder(this);
        n.setContentTitle(title);
        n.setContentText(description);
        n.setSmallIcon(R.mipmap.ic_launcher);
        n.setAutoCancel(true);
        n.setContentIntent(p);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());
    }
}
