package com.example.myapplication.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import com.example.myapplication.wifidirect.ClientActivity

class WifiDirectBroadcastReceiver(
    wifiP2pManager: WifiP2pManager,
    channel: WifiP2pManager.Channel,
    activity: ClientActivity
) : BroadcastReceiver() {
    var wifiP2pManager: WifiP2pManager
    var channel: WifiP2pManager.Channel
    var activity: ClientActivity

    init {
        this.wifiP2pManager = wifiP2pManager
        this.channel = channel
        this.activity = activity
    }

    override fun onReceive(context: Context?, intent: Intent?) {
//        var action: String? = intent?.action
//        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION == action) {
//            var state: Int = intent!!.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
//            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show()
//            }
//        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION == action) {
//            wifiP2pManager.requestPeers(channel, activity.peerListListener)
//        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION == action) {
//
//        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION == action) {
//
//        }
    }
}