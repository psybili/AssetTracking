package sample.onursaygili.assettracking.data.remote

import sample.onursaygili.assettracking.data.local.Location

fun getMapUrl(width: Int, height: Int, locations: List<Location>): String {
    val sb = StringBuilder()
    sb.append("https://maps.googleapis.com/maps/api/staticmap?size=${width}x${height}")
    sb.append("&path=color:0x58116665|weight:5")
    for (location in locations) {
        sb.append("|${location.latitude},${location.longitude}")
    }
    sb.append("&markers=color:red%7Clabel:A%7C${locations.first().latitude},${locations.first().longitude}")
    sb.append("&markers=color:red%7Clabel:B%7C${locations.last().latitude},${locations.last().longitude}")
    sb.append("&sensor=false")
    return sb.toString()
}