package com.waleed.nevermiss.ui.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat


class AutoMsgService : AccessibilityService() {



    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (rootInActiveWindow == null) {
            return
        }

        val rootInActiveWindow = AccessibilityNodeInfoCompat.wrap(rootInActiveWindow)

        // Whatsapp Message EditText id
        val messageNodeList =
            rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry")
        if (messageNodeList == null || messageNodeList.isEmpty()) {
            return
        }

        // check if the whatsapp message EditText field is filled with text and ending with your suffix (explanation above)
        val messageField = messageNodeList[0]


        if (messageField.text == null || messageField.text.isEmpty()

        //  || !messageField.text.toString().endsWith(applicationContext.getString(R.string.whatsapp_suffix))

        ) { // So your service doesn't process any message, but the ones ending your apps suffix
            return
        }


        // Whatsapp send button id
        val sendMessageNodeInfoList =
            rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send")
        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty()) {
            return
        }

        val sendMessageButton = sendMessageNodeInfoList[0]
        if (!sendMessageButton.isVisibleToUser) {
            return
        }

        // Now fire a click on the send button
        sendMessageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)

        // Now go back to your app by clicking on the Android back button twice:
        // First one to leave the conversation screen
        // Second one to leave whatsapp


        try {
            Thread.sleep(500) // hack for certain devices in which the immediate back click is too fast to handle
            performGlobalAction(GLOBAL_ACTION_BACK)
            Thread.sleep(500)  // same hack as above
        } catch (ignored: InterruptedException) {
        }
        performGlobalAction(GLOBAL_ACTION_BACK)
    }
}