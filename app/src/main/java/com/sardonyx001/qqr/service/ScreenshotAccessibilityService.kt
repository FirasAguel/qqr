//package com.sardonyx001.qqr.service;
package com.sardonyx001.qqr;
//package com.sardonyx001.android.apis.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.core.content.ContextCompat
import com.sardonyx001.qqr.receiver.ScreenshotBroadcastReceiver



class ScreenshotAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {
        Log.d("TAG", "interrupt")}

    private var previousPackageName: String? = null

    var isTileServiceTriggered = false;

    //val screenshotBroadcastReceiver = ScreenshotBroadcastReceiver()
    val filter = IntentFilter("com.sardonyx001.qqr.TAKE_SCREENSHOT")
    val receiverFlags = ContextCompat.RECEIVER_EXPORTED

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("AccessibilityEvent", "accesibility event")

        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val currentPackageName = event.packageName.toString()
            Log.d("AccessibilityEvent", "WINDOW_STATE_CHANGED")

            // If the package name has changed, it means a new app has come into focus
            if (currentPackageName != previousPackageName) {
                previousPackageName = currentPackageName
                Log.d("AccessibilityEvent", "package changed")

                // Check if the Tile Service trigger intent has been received
                if (isTileServiceTriggered) {
                    // Perform the screenshot action on the previous app
                    //val intent = Intent(AccessibilityEvent.ACTION_GLOBAL_SHOW_RECENTS)
                    // sendBroadcast(intent)
                    //Thread.sleep(500) // Adjust the delay as needed
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT)
                    isTileServiceTriggered = false
                }
            }
        }
    }

}