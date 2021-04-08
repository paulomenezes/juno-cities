package com.juno.cities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Package(val local: String, val imagem: String, val dias: String, val preco: String): Parcelable