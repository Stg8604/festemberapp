package edu.nitt.deltaapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "edu.nitt.deltaapp"

class MyFirebaseMessagingService : FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage) {

    if (remoteMessage.getNotification() != null) {
      generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
    }
  }

  fun getRemoteView(title: String, message: String): RemoteViews {
    val remoteView = RemoteViews(channelName, R.layout.notification)
    remoteView.setTextViewText(R.id.title, title)
    remoteView.setTextViewText(R.id.notif_description, message)
    remoteView.setImageViewResource(R.id.notif_logo, R.mipmap.festicon)
    return remoteView
  }

  fun generateNotification(title: String, message: String) {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(
      this,
      0,
      intent,
      PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
    )
    var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
      .setSmallIcon(R.drawable.festemberlogo23app)
      .setAutoCancel(true)
      .setVibrate(
        longArrayOf(
          1000, 1000, 1000,
          1000, 1000
        )
      )
      .setOnlyAlertOnce(true)
      .setContentIntent(pendingIntent)

    builder = builder.setContent(getRemoteView(title, message))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
      notificationManager!!.createNotificationChannel(notificationChannel)
    }
    notificationManager!!.notify(0, builder.build())
  }
}
