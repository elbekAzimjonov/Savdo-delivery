package com.imsoft.savdodelivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TotalPrice(
    val AMD: Int,
    val BOB: Double,
    val GBP: Int,
    val GEL: Int,
    val GMD: Int,
    val HUF: Int,
    val KMF: Int,
    val LRD: Int,
    val MMK: Int,
    val MWK: Int,
    val MYR: Int,
    val NGN: Int,
    val NZD: Double,
    val SRD: Int,
    val THB: Int,
    val TND: Int,
    val UAH: Int,
    val UGX: Int,
    val UZS: Int,
    val XCD: Int
): Parcelable