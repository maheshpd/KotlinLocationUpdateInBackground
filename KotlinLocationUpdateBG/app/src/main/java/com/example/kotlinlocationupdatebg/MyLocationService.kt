package com.example.kotlinlocationupdatebg

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import com.google.android.gms.location.LocationResult

class MyLocationService : BroadcastReceiver() {

    companion object {
        val ACTION_PROCESS_UPDATE = "com.example.kotlinlocationupdatebg.UPDATE_LOCATION"
    }

    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"


    override fun onReceive(context: Context?, intent: Intent?) {
        notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (intent != null) {
            val action = intent.action
            if (action.equals(ACTION_PROCESS_UPDATE)) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val location = result.lastLocation
                    val latitude: Double = location.latitude
                    val longitude: Double = location.longitude

                    val location_string = StringBuilder(location.latitude.toString())
                            .append("/").append(location.longitude).toString()

                    try {
                        MainActivity.getMainInstance().updateTextView(location_string)
                    } catch (e: Exception) {
                        //if app in killes mode
                        Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show()
                        createNotification(context, latitude, longitude)
                    }
                }
            }
        }
    }

    private fun createNotification(context: Context, latitude: Double, longitude: Double) {
        val intent = Intent(context, MapsActivity::class.java)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews("com.example.kotlinlocationupdatebg", R.layout.activity_after_notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(context)
                    .setContent(contentView
                    ).setVibrate(listOf(1L, 2L, 3L).toLongArray())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

/*
    fun createNotification(context: Context?){
        val intent = Intent(context, MapsActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews("com.example.kotlinlocationupdatebg",R.layout.activity_after_notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(context)
                    .setContent(contentView
                    ).setVibrate(listOf(1L, 2L, 3L).toLongArray())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }
*/


}