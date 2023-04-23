package com.example.myapplication;

import a1.b;
import a1.e;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import d.d;
import java.util.ArrayList;
import java.util.Collection;
import m2.c;

/* loaded from: classes.dex */
public final class ClientActivity extends d {
    public static final /* synthetic */ int D = 0;
    public IntentFilter A;
    public ArrayList B = new ArrayList();
    public a C = new a();
    public Button u;

    /* renamed from: v  reason: collision with root package name */
    public Button f1790v;

    /* renamed from: w  reason: collision with root package name */
    public WifiManager f1791w;

    /* renamed from: x  reason: collision with root package name */
    public WifiP2pManager f1792x;

    /* renamed from: y  reason: collision with root package name */
    public WifiP2pManager.Channel f1793y;

    /* renamed from: z  reason: collision with root package name */
    public BroadcastReceiver f1794z;

    /* loaded from: classes.dex */
    public static final class a implements WifiP2pManager.PeerListListener {
        public a() {
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
        public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            c.b(wifiP2pDeviceList);
            if (!wifiP2pDeviceList.getDeviceList().equals(ClientActivity.this.B)) {
                ClientActivity.this.B.clear();
                ArrayList arrayList = ClientActivity.this.B;
                Collection<WifiP2pDevice> deviceList = wifiP2pDeviceList.getDeviceList();
                c.c(deviceList, "peerList.deviceList");
                arrayList.addAll(deviceList);
                for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                    Log.d("test", c.f(wifiP2pDevice.deviceName, "Device Name: "));
                }
                Log.d("test", c.f(Integer.valueOf(wifiP2pDeviceList.getDeviceList().size()), "peerList"));
                int i3 = 0;
                int size = wifiP2pDeviceList.getDeviceList().size();
                if (size >= 0) {
                    while (true) {
                        int i4 = i3 + 1;
                        Log.d("test", c.f(wifiP2pDeviceList.getDeviceList(), "Device Name: "));
                        if (i3 == size) {
                            break;
                        }
                        i3 = i4;
                    }
                }
            }
            if (ClientActivity.this.B.size() == 0) {
                Log.d("test", "No Devices Found");
            }
        }
    }

    @Override // androidx.fragment.app.q, androidx.activity.ComponentActivity, v.d, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_client);
        View findViewById = findViewById(R.id.buttonOnWifi);
        c.c(findViewById, "findViewById(R.id.buttonOnWifi)");
        this.u = (Button) findViewById;
        View findViewById2 = findViewById(R.id.buttonDiscover);
        c.c(findViewById2, "findViewById(R.id.buttonDiscover)");
        this.f1790v = (Button) findViewById2;
        Object systemService = getApplicationContext().getSystemService("wifi");
        if (systemService != null) {
            this.f1791w = (WifiManager) systemService;
            Object systemService2 = getSystemService("wifip2p");
            if (systemService2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.net.wifi.p2p.WifiP2pManager");
            }
            WifiP2pManager wifiP2pManager = (WifiP2pManager) systemService2;
            this.f1792x = wifiP2pManager;
            WifiP2pManager.Channel initialize = wifiP2pManager.initialize(this, Looper.getMainLooper(), null);
            c.c(initialize, "manager.initialize(this,…er.getMainLooper(), null)");
            this.f1793y = initialize;
            Button button = this.u;
            if (button == null) {
                c.g("buttonOnWifi");
                throw null;
            }
            WifiManager wifiManager = this.f1791w;
            if (wifiManager == null) {
                c.g("wifiManager");
                throw null;
            }
            button.setText(wifiManager.isWifiEnabled() ? "OFF" : "ON");
            Button button2 = this.u;
            if (button2 == null) {
                c.g("buttonOnWifi");
                throw null;
            }
            button2.setOnClickListener(new a1.a(0, this));
            Button button3 = this.f1790v;
            if (button3 == null) {
                c.g("buttonDiscover");
                throw null;
            }
            button3.setOnClickListener(new b(0, this));
            WifiP2pManager wifiP2pManager2 = this.f1792x;
            if (wifiP2pManager2 == null) {
                c.g("manager");
                throw null;
            }
            WifiP2pManager.Channel channel = this.f1793y;
            if (channel == null) {
                c.g("channel");
                throw null;
            }
            this.f1794z = new e(wifiP2pManager2, channel, this);
            this.A = new IntentFilter();
            r().addAction("android.net.wifi.p2p.STATE_CHANGED");
            r().addAction("android.net.wifi.p2p.PEERS_CHANGED");
            r().addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
            r().addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.net.wifi.WifiManager");
    }

    @Override // androidx.fragment.app.q, android.app.Activity
    public final void onPause() {
        super.onPause();
        BroadcastReceiver broadcastReceiver = this.f1794z;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        } else {
            c.g("receiver");
            throw null;
        }
    }

    @Override // androidx.fragment.app.q, android.app.Activity
    public final void onResume() {
        super.onResume();
        BroadcastReceiver broadcastReceiver = this.f1794z;
        if (broadcastReceiver != null) {
            registerReceiver(broadcastReceiver, r());
        } else {
            c.g("receiver");
            throw null;
        }
    }

    public final IntentFilter r() {
        IntentFilter intentFilter = this.A;
        if (intentFilter != null) {
            return intentFilter;
        }
        c.g("intentFilter");
        throw null;
    }
}