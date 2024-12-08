package com.sardonyx001.qqr.service

import android.content.Intent
import android.service.quicksettings.TileService
import android.graphics.Bitmap

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

    private fun showLinkDialog(links: ArrayList<String>) {
        val intent = Intent(this, LinkDialogService::class.java).apply {
            putStringArrayListExtra("LINKS", links)
        }
        startService(intent)
    }

    private fun captureAndScanScreenshot() {
        TODO()
    }

    private fun convertImageToBitmap(image: android.media.Image): Bitmap {
        TODO()
    }

    private fun scanQRCodes(bitmap: Bitmap) {
        TODO()
    }


}