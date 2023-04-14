package com.qbit.qbitgamech.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qbit.qbitgamech.homeui.home.Home;

import java.time.LocalDateTime;
import java.util.Objects;

public class FCM extends FirebaseMessagingService {
    String result;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM Token", "Refreshed token: " + token);
    }

    public void subscribe(String topicName) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName)
                .addOnCompleteListener(task -> {
                    Log.d("FCM Topic Subscription", "User subscribed to " + topicName + " successfully.");
                    if (!task.isSuccessful()) {
                        Log.d("FCM Topic Subscription", "User failed to subscribe " + topicName);
                    }

                });
    }

    public void unsubscribe(String topicName) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicName)
                .addOnCompleteListener(task -> {
                    Log.d("FCM Topic Subscription", "User unsubscribed from " + topicName + " successfully.");
                    if (!task.isSuccessful()) {
                        Log.d("FCM Topic Subscription", "User failed to unsubscribe " + topicName);
                    }

                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("FCM Message", message.getNotification().getBody());
        if (message.getData().size() > 0) {
            Log.d("Notification", "Message data payload: " + message.getData());
            // Handle message within 10 seconds
        }
        // Check if message contains a notification payload.
        if (message.getNotification() != null) {
            Log.d("Notification", "Message Notification Body: " + message.getNotification().getBody());
            notifyUser(message);
        }

    }

    private void notifyUser(RemoteMessage message) {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        Notification notification = new Notification.Builder(this, Objects.requireNonNull(message.getNotification().getChannelId()))
                .setSmallIcon(Icon.createWithContentUri(message.getNotification().getImageUrl()) )
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setContentIntent(pendingIntent)
                .setChannelId(message.getNotification().getChannelId())
                .setAutoCancel(true).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d("Notification", "Notification Sent to user.");
        notificationManager.notify(LocalDateTime.now().getNano(), notification);

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
