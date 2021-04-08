package com.juno.cities.jobs

import android.app.NotificationManager
import android.content.Context
import android.webkit.URLUtil
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.juno.cities.R
import java.io.*
import java.net.URL


class DownloadWork(private val context: Context, private val workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private fun download(link: String, file: File) {
        URL(link).openStream().use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
    }

    override fun doWork(): Result {
        val url = inputData.getString("URL")
        val name = inputData.getString("NAME")

        if (url != null && name != null && URLUtil.isValidUrl(url)) {
            val file = File(context.filesDir, name)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val id1 = if (name == "cidades.zip") 10 else 20
            val id2 = if (name == "cidades.zip") 11 else 21

            notificationManager.notify(id1, NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Download")
                .setContentText("Download iniciado $name")
                .build()
            )

            download(url, file)

            notificationManager.notify(id2, NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Download")
                .setContentText("Download finalizado $name")
                .build()
            )

            val outputData = Data.Builder().putString(
                "file",
                file.absolutePath
            ).build()

            return Result.success(outputData)
        }

        return Result.failure()
    }
}