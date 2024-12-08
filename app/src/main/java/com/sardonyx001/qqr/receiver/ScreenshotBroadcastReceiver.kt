package com.sardonyx001.qqr.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import com.sardonyx001.qqr.ScreenshotAccessibilityService


class ScreenshotBroadcastReceiver : BroadcastReceiver() {

    fun takeScreenshotOfView(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        //Log.d("BroadcastReceiver", "Broadcast received")
        val action = intent?.action
        //Log.d("BroadcastReceiver", "Received action: $action") // 受信したアクションを確認
        if (action == "com.sardonyx001.qqr.TAKE_SCREENSHOT") {
            Log.d("BroadcastReceiver", "screenshot Broadcast received")
            val serviceIntent = Intent(context, ScreenshotAccessibilityService::class.java)
            serviceIntent.action = "com.sardonyx001.qqr.TAKE_SCREENSHOT"
            context?.startService(serviceIntent)

        }
    }
}
