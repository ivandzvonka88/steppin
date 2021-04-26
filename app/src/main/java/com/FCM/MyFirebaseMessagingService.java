package com.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.steppin.BuildConfig;
import com.steppin.Home;
import com.steppin.Notification_Activity;
import com.steppin.R;
import com.steppin.TempData;
import com.utils.MySession;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Raj on 11/12/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    NotificationCompat.Builder notificationBuilder;
    public static final String BROADCAST_ACTION = "com.steppin.msg";
    Intent intentChat;

    NotificationManager notificationManager;
    Bitmap image;
    public static final String Key_image = "image";
    public static final String Key_message = "message";
    public static final String Key_title = "title";
    public static final String Key_type = "type";
    public static final String Key_id = "id";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
        MySession.setAccessToken(getApplicationContext(), s);
        if (BuildConfig.FLAVOR == "steppinadmin") {
            FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleAdmin);
            MySession.set(getApplicationContext(),"1",TempData.NotiChannaleAdmin);
        } else if (BuildConfig.FLAVOR == "steppin") {
            FirebaseMessaging.getInstance().subscribeToTopic(TempData.NotiChannaleClients);
            MySession.set(getApplicationContext(),"1",TempData.NotiChannaleClients);
        }

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MyActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Log.e("newToken",newToken);
//
//            }
//        });
    }

    public void subscribeTopic() {

//        String s = "" + MySession.getAccessTokenTopic(getApplicationContext());
//        if (s.equals("") || s.toLowerCase().equals("null")) {
//            Random rand = new Random();
//            int n = rand.nextInt(5) + 1;
//            s = "Steppin" + n;
//            MySession.setAccessTokenTopic(getApplicationContext(), s);
//        }
//        Log.e("Topic", "-" + s);
//        if (!s.equals("") && !s.equals("null")) {
//            FirebaseMessaging.getInstance().subscribeToTopic(s);
//        }

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.e(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "FCM Data: " + remoteMessage.getData());

//        String msg = remoteMessage.getData().get("message");
        String img = "", msg = "", title = "", type = "", id = "";
        intentChat = new Intent(BROADCAST_ACTION);

        try {
            img = remoteMessage.getData().get(Key_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img == null) {
            img = "";
        }
        Bundle b = new Bundle();
        b.putString(Key_image, img);
        try {
            msg = remoteMessage.getData().get(Key_message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg == null) {
            msg = "";
        }
        b.putString(Key_message, msg);
        try {
            title = remoteMessage.getData().get(Key_title);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (title == null) {
            title = "";
        }
        b.putString(Key_title, title);
        try {
            type = remoteMessage.getData().get(Key_type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (type == null) {
            type = "";
        }
        b.putString(Key_type, type);
        try {
            id = remoteMessage.getData().get(Key_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id == null) {
            id = "";
        }
        b.putString(Key_id, id);
        Log.e("Pass ID", "-" + id);

        if (!img.equals("") && !img.equals("null")) {
            image = getBitmapFromURL(img);
        }
        String time = System.currentTimeMillis() + "";
        ArrayList<HashMap<String, String>> Noti_Array = new ArrayList<>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Notification_Data.Key_NotificationTitle, title);
        map.put(Notification_Data.Key_Notification, msg);
        map.put(Notification_Data.Key_Time, time);
        map.put(Notification_Data.Key_type, type);
        map.put(Notification_Data.Key_NotificationImage, img);
        map.put(Notification_Data.Key_IsRead, "0");
        Noti_Array.add(map);
        //Calling method to generate notification
        if (MySession.ShareNewsNotification.equals(type)) {
            Notification_Data.addNotiArray(getApplicationContext(), Noti_Array);
        }
        boolean is = false;
        if (MySession.ShareNewsNotification.equals(type) && MySession.getNoti(getApplicationContext(), MySession.ShareNewsNotification)) {
            is = true;
        }else  if (MySession.ShareEventsNotification.equals(type) && MySession.getNoti(getApplicationContext(), MySession.ShareEventsNotification)) {
            is = true;
        }else  if (MySession.ShareVenuesNotification.equals(type) && MySession.getNoti(getApplicationContext(), MySession.ShareVenuesNotification)) {
            is = true;
        }else  if (MySession.ShareDanceNotification.equals(type) && MySession.getNoti(getApplicationContext(), MySession.ShareDanceNotification)) {
            is = true;
        }
        if (is) {
            sendNotificationNoti(title, msg, image, img, type, id);
        }


    }

    private void sendNotificationNoti(String Title, String messageBody, Bitmap img, String strImage, String sType, String sID) {

        Intent intent = new Intent(this, Home.class);
        if (MySession.ShareNewsNotification.equals(sType)) {
            intent = new Intent(this, Notification_Activity.class);
        }else  if (MySession.ShareEventsNotification.equals(sType)) {
            intent = new Intent(this, Home.class);
        }else  if (MySession.ShareVenuesNotification.equals(sType)) {
            intent = new Intent(this, Home.class);
        }else  if (MySession.ShareDanceNotification.equals(sType)) {
            intent = new Intent(this, Home.class);
        }
        Bundle b = new Bundle();
        b.putString(Notification_Data.Key_Notification, messageBody);
        b.putString(Notification_Data.Key_NotificationTitle, Title);
        b.putString(Notification_Data.Key_NotificationImage, strImage);
        b.putString(Notification_Data.Key_type, sType);
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        int notiIcon;

        if (Title.equals("") || Title.toLowerCase().equals("null")) {
            Title = getApplicationContext().getResources().getString(R.string.app_name);
        }

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannelForAndroidO();

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.LOLLIPOP) {
        notiIcon = R.drawable.notification_icon;
//        } else {
//            notiIcon = R.drawable.noti_logo_white;
//        }
        long[] pattern = {500, 500, 500, 500, 500};
        if (img != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                messageBody = messageBody.replaceAll("\\s+", "");
                messageBody = messageBody.replaceAll("[\n\r]", "");
                messageBody = messageBody.replaceAll("\r", "");
            }
            notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(notiIcon)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(img))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLights(Color.BLUE, 1, 1)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(notiIcon)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(Title))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLights(Color.BLUE, 1, 1)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent);
        }


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    String NOTIFICATION_CHANNEL_ID = "SteppinApp";

    private void createNotificationChannelForAndroidO() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Steppin Notification.", NotificationManager.IMPORTANCE_HIGH);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // Configure the notification channel.
            notificationChannel.setDescription("Steppin Notification.");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}