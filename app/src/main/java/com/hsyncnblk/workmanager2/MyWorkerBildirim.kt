package com.hsyncnblk.workmanager2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerBildirim(appContext: Context, workerParams: WorkerParameters) : Worker(appContext,workerParams) {
    override fun doWork(): Result {

        return Result.success()
    }

    fun bildirimOlustur(){
        val builder:NotificationCompat.Builder
        val bildirimYonetcisi =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext,MainActivity::class.java)
        val gidilecekIntent = PendingIntent.getActivity(
            applicationContext,
            1,intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            val kanalId = "kanalId"
            val kanalAd= "kanalAd"
            val kanalTanaitim = "kanalTanitim"
            val kanalOnceligi = NotificationManager.IMPORTANCE_HIGH
            var kanal : NotificationChannel? =
                bildirimYonetcisi.getNotificationChannel(kanalId)

            if (kanal== null){
                kanal=NotificationChannel(kanalId,kanalAd,kanalOnceligi)
                kanal.description= kanalTanaitim
                bildirimYonetcisi.createNotificationChannel(kanal)
            }

            builder = NotificationCompat.Builder(applicationContext,kanalId)

            builder.setContentTitle("başlık")
                .setContentText("İÇERİK")
                .setSmallIcon(R.drawable.resim)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)



        }else{
            builder = NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("başlık")
                .setContentText("İÇERİK")
                .setSmallIcon(R.drawable.resim)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH

        }

        bildirimYonetcisi.notify(1,builder.build())

    }
}