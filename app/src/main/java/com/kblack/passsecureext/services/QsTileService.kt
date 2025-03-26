package com.kblack.passsecureext.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.kblack.passsecureext.R
import com.kblack.passsecureext.activities.MainActivity
import com.kblack.passsecureext.objects.AppState

@RequiresApi(Build.VERSION_CODES.N)
class QsTileService : TileService() {
    @SuppressLint("ObsoleteSdkInt")
    override fun onStartListening() {
        super.onStartListening()
        val isAppOpen = AppState.isAppOpen

        qsTile.apply {
            state = if (isAppOpen) Tile.STATE_UNAVAILABLE else Tile.STATE_UNAVAILABLE
            if (Build.VERSION.SDK_INT >= 29) subtitle = if (isAppOpen) getString(R.string.app_in_use) else null
        }
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        val intent =
            Intent(this, MainActivity::class.java).apply {
                putExtra("shortcut", "shortcutGenerate")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

        if (Build.VERSION.SDK_INT >= 34) {
            startActivityAndCollapse(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}