package com.sardonyx001.qqr.service

import android.content.Intent
import android.content.IntentFilter
import android.service.quicksettings.TileService
import android.util.Log
import com.sardonyx001.qqr.receiver.ScreenshotBroadcastReceiver


class QrReaderTileService: TileService() {

    // Called when the user adds your tile.
    override fun onTileAdded() {
    }
    // Called when your app can update your tile.
    override fun onStartListening() {
        super.onStartListening()
    }

    // Called when your app can no longer update your tile.
    override fun onStopListening() {
        super.onStopListening()
    }

    lateinit var receiver: ScreenshotBroadcastReceiver
    private lateinit var screenshotBroadcastReceiver: ScreenshotBroadcastReceiver

    var isReceiverRegistered = false

    // Called when the user taps on your tile in an active or inactive state.
    override fun onClick() {
        super.onClick()
        val intent = Intent("com.sardonyx001.qqr.TAKE_SCREENSHOT")
        Log.d("TAG", "Sending broadcast with action: ${intent.action}")
        sendBroadcast(intent)
        super.onTileAdded()

        if(!isReceiverRegistered) {
            screenshotBroadcastReceiver = ScreenshotBroadcastReceiver()
            val intentFilter = IntentFilter("com.sardonyx001.qqr.TAKE_SCREENSHOT")
            registerReceiver(screenshotBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
            isReceiverRegistered = true
        }

    }
    // Called when the user removes your tile.
    override fun onTileRemoved() {
        super.onTileRemoved()
    }
}