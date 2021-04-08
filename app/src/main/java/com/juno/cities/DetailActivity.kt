package com.juno.cities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val city = intent.getParcelableExtra<Package>("city")

        if (city != null) {
            findViewById<TextView>(R.id.detailLocal).text = city.local
            findViewById<TextView>(R.id.detailDias).text = city.dias
            findViewById<TextView>(R.id.detailPreco).text = city.preco

            if (File("$filesDir/cidades/${city.imagem}").exists()) {
                findViewById<ImageView>(R.id.imageView).setImageURI(Uri.parse("$filesDir/cidades/${city.imagem}"))
            }
        }
    }
}