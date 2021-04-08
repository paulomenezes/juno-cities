package com.juno.cities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.beust.klaxon.Klaxon
import com.juno.cities.jobs.DownloadWork
import java.io.File

class MainActivity : AppCompatActivity() {
    private val adapter: CitiesAdapter by lazy { CitiesAdapter(this) { city ->
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("city", city)

        startActivity(intent)
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val channel = NotificationChannel("channel_id", "channel", NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)
        }

        val downloadCitiesJSON = OneTimeWorkRequest
            .Builder(DownloadWork::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(
                Data
                    .Builder()
                    .putString("URL", "https://raw.githubusercontent.com/haldny/imagens/main/pacotes.json")
                    .putString("NAME", "pacotes.json")
                    .build()
            ).build()

        val downloadImages = OneTimeWorkRequest
            .Builder(DownloadWork::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(
                Data
                    .Builder()
                    .putString("URL", "https://github.com/haldny/imagens/raw/main/cidades.zip")
                    .putString("NAME", "cidades.zip")
                    .build()
            ).build()

        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(downloadCitiesJSON)
        workManager.enqueue(downloadImages)
        workManager.getWorkInfoByIdLiveData(downloadCitiesJSON.id).observe(this, {
            if (it.state.isFinished) {

                Log.d("JUNO", "Download finalizado")
                it.outputData.getString("file")?.let { it1 ->
                    Log.d("JUNO", it1)

                    val result = Klaxon()
                        .parse<Result>(File(it1).readText())

                    if (result != null) {
                        adapter.submitList(result.pacotes)
                    }

                    findViewById<Button>(R.id.button).isEnabled = true
                }
            }
        })

        workManager.getWorkInfoByIdLiveData(downloadImages.id).observe(this, {
            if (it.state.isFinished) {
                Log.d("JUNO", "Download images finalizado")
                it.outputData.getString("file")?.let { it1 ->
                    Log.d("JUNO", it1)

                    UnzipUtils.unzip(File(it1), "${filesDir.absolutePath}/cidades")

                    Log.d("JUNO", "unzipped")
                }
            }
        })
    }
}