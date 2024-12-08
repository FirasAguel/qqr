package com.sardonyx001.qqr.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import com.sardonyx001.qqr.R

class LinkDialogService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val links = intent?.getStringArrayListExtra("LINKS") ?: return START_NOT_STICKY

        createOverlayDialog(links)
        return START_NOT_STICKY
    }

    private fun createOverlayDialog(links: ArrayList<String>) {
        // Remove any existing overlay
        if (::overlayView.isInitialized) {
            try {
                windowManager.removeView(overlayView)
            } catch (_: Exception) {}
        }

        // Inflate the overlay layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.links_dialog, null)

        // Layout parameters for the overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        // Find the container for links
        val linksContainer = overlayView.findViewById<LinearLayout>(R.id.linksContainer)

        // Create buttons for each link
        links.forEach { link ->
            Button(this).apply {
                text = link
                setOnClickListener {
                    // Open link in browser
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(browserIntent)

                    // Remove overlay and stop service
                    stopSelf()
                }
                linksContainer.addView(this)
            }
        }

        // Cancel button
        overlayView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            stopSelf()
        }

        // Add the overlay to the window
        windowManager.addView(overlayView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the overlay if it exists
        if (::overlayView.isInitialized) {
            try {
                windowManager.removeView(overlayView)
            } catch (_: Exception) {}
        }
    }
}

