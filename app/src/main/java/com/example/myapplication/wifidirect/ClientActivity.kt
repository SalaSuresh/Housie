package com.example.myapplication.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class ClientActivity : AppCompatActivity() {
    private lateinit var buttonOnWifi: Button
    private lateinit var buttonDiscover: Button
    private lateinit var buttonConnect: Button
    lateinit var wifiManager: WifiManager

    lateinit var manager: WifiP2pManager
    lateinit var channel: WifiP2pManager.Channel

    lateinit var receiver: BroadcastReceiver
    lateinit var intentFilter: IntentFilter

    lateinit var deviceAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        buttonOnWifi = findViewById(R.id.buttonOnWifi)
        buttonDiscover = findViewById(R.id.buttonDiscover)
        buttonConnect = findViewById(R.id.buttonConnect)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        manager = getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, Looper.getMainLooper(), null)

        buttonOnWifi.text = if (wifiManager.isWifiEnabled) {
            "OFF"
        } else {
            "ON"
        }
        buttonOnWifi.setOnClickListener {
            turnOnOffWifi()
        }
        buttonDiscover.setOnClickListener {
            startDiscovery()
        }
        buttonConnect.setOnClickListener {
            connectDevice()
        }

        receiver = WifiDirectBroadcastReceiver(manager, channel, this)
        intentFilter = IntentFilter()
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

    }

    private fun connectDevice() {
        val wifiP2pConfig = WifiP2pConfig()
        wifiP2pConfig.deviceAddress = deviceAddress
//        manager.connect(channel, wifiP2pConfig, object: WifiP2pManager.ActionListener{
//            override fun onSuccess() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(p0: Int) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    private fun startDiscovery() {
//        manager.discoverPeers(channel, object : ActionListener {
//            override fun onSuccess() {
//                Log.d("test", "Discovery Started")
//            }
//
//            override fun onFailure(p0: Int) {
//                Log.d("test", "-----------------------Discovery Starting failed $p0")
//            }
//
//        })
    }

    private fun turnOnOffWifi() {
        if (wifiManager.isWifiEnabled) {
            buttonOnWifi.text = "ON"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
                startActivityForResult(panelIntent, 0)
            } else {
                wifiManager.isWifiEnabled = false
            }
        } else {
            buttonOnWifi.text = "OFF"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
                startActivityForResult(panelIntent, 1)
            } else {
                wifiManager.isWifiEnabled = true
            }
        }
    }

    //    companion object {
    var peers: MutableList<WifiP2pDevice> = ArrayList()
    lateinit var deviceNameArray: Array<String>
    lateinit var deviceArray: Array<WifiP2pDevice>

    var peerListListener: WifiP2pManager.PeerListListener =
        object : WifiP2pManager.PeerListListener {
            override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {
                Log.d("test", "onPeersAvailable() called with: peerList = $peerList")
                if (!peerList!!.deviceList.equals(peers)) {
                    peers.clear()
                    peers.addAll(peerList.deviceList)

                    for (device in peerList.deviceList) {
//                        deviceNameArray[index] = device.deviceName
//                        deviceArray[index] = device
//                        index++
                        Log.d("test", "Device Name: " + device.deviceName)
                        Log.d("test", "Device Address: " + device.deviceAddress)
                        Log.d("test", "Device Name: " + device.primaryDeviceType)
                        Log.d("test", "Device Name: " + device.secondaryDeviceType)
                        for (informationElement: ScanResult.InformationElement in if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            device.vendorElements
                        } else {
                            TODO("VERSION.SDK_INT < TIRAMISU")
                        })
                            Log.d("test", "Device Name: ${informationElement.id}")

                        deviceAddress = device.deviceAddress
                    }

                    Log.d("test", "peerList${peerList.deviceList.size}")
                    for (i in 0..peerList.deviceList.size) {
                        Log.d("test", "Device Name: " + peerList.deviceList)
                    }
                }

                if (peers.size == 0) {
                    Log.d("test", "No Devices Found")
                    return
                }
            }

        }
    /*var peerListListener =
        PeerListListener { peerList ->
            if (peerList.deviceList != peers) {
                peers.clear()
                peers.addAll(peerList.deviceList)
                deviceNameArray = arrayOfNulls<String>(peerList.deviceList.size)
                deviceArray = arrayOfNulls<WifiP2pDevice>(peerList.deviceList.size)
                var index = 0
                // maybe we can get more information here
                for (device in peerList.deviceList) {
                    deviceNameArray.get(index) = device.deviceName
                    deviceArray.get(index) = device
                    index++
                }
                Log.d("this is my array", "arr: " + Arrays.toString(deviceNameArray))
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    deviceNameArray
                )
                listView.setAdapter(adapter)
            }
            if (peers.size == 0) {
                Log.d("WIFIDIRECT", "No devices found")
            }
        }*/
//    }


    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}