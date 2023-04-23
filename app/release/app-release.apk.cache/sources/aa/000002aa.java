package com.example.myapplication;

import a1.a;
import a1.b;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import d.d;
import m2.c;

/* loaded from: classes.dex */
public final class WifiDirectActivity extends d {
    public static final /* synthetic */ int u = 0;

    @Override // androidx.fragment.app.q, androidx.activity.ComponentActivity, v.d, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_wifi_direct);
        View findViewById = findViewById(R.id.buttonClient);
        c.c(findViewById, "findViewById(R.id.buttonClient)");
        ((Button) findViewById).setOnClickListener(new b(3, this));
        View findViewById2 = findViewById(R.id.buttonAdmin);
        c.c(findViewById2, "findViewById(R.id.buttonAdmin)");
        ((Button) findViewById2).setOnClickListener(new a(2, this));
    }
}