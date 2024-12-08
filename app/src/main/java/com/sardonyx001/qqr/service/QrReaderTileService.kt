package com.sardonyx001.qqr.service

import android.content.Intent
import android.service.quicksettings.TileService
import android.graphics.Bitmap
import android.content.IntentFilter
import android.service.quicksettings.TileService
import android.util.Log
import com.sardonyx001.qqr.receiver.ScreenshotBroadcastReceiver

class QrReaderTileService: TileService() {
    override fun onClick() {
        super.onClick()

        val testLinks = ArrayList<String>().apply {
            add("https://www.google.com")
            add("https://www.github.com")
            add("https://www.stackoverflow.com")
            add("https://www.anthropic.com")
        }

        showLinkDialog(testLinks)

//        // Create Intent
//        val intent = Intent(this, LinkDialogActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            putExtra("LINKS", testLinks)
//        }
//        // Create PendingIntent
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        startActivityAndCollapse(pendingIntent)
    }
    // Called when the user adds your tile.
    override fun onTileAdded() {
    }

    private fun showLinkDialog(links: ArrayList<String>) {
        val intent = Intent(this, LinkDialogService::class.java).apply {
            putStringArrayListExtra("LINKS", links)
        }
        startService(intent)
    }

    private fun captureAndScanScreenshot() {
        TODO()
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

    private fun scanQRCodes(bitmap: Bitmap) {
        TODO()
    }


}
