package com.example.myapplication;

import a1.b;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NumberCallerActivity;
import com.example.myapplication.WifiDirectActivity;
import m2.c;

/* loaded from: classes.dex */
public final class MainActivity extends Activity {

    /* renamed from: e  reason: collision with root package name */
    public static final /* synthetic */ int f1796e = 0;

    /* renamed from: b  reason: collision with root package name */
    public Button f1797b;
    public Button c;

    /* renamed from: d  reason: collision with root package name */
    public Button f1798d;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        View findViewById = findViewById(R.id.buttonNumberCaller);
        c.c(findViewById, "findViewById(R.id.buttonNumberCaller)");
        this.f1797b = (Button) findViewById;
        View findViewById2 = findViewById(R.id.buttonTicketGenerator);
        c.c(findViewById2, "findViewById(R.id.buttonTicketGenerator)");
        this.c = (Button) findViewById2;
        View findViewById3 = findViewById(R.id.buttonWifiDirect);
        c.c(findViewById3, "findViewById(R.id.buttonWifiDirect)");
        this.f1798d = (Button) findViewById3;
        Button button = this.f1797b;
        if (button == null) {
            c.g("buttonNumberCaller");
            throw null;
        }
        button.setOnClickListener(new View.OnClickListener(this) { // from class: a1.d
            public final /* synthetic */ MainActivity c;

            {
                this.c = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (r2) {
                    case 0:
                        MainActivity mainActivity = this.c;
                        int i3 = MainActivity.f1796e;
                        m2.c.d(mainActivity, "this$0");
                        mainActivity.startActivity(new Intent(mainActivity, NumberCallerActivity.class));
                        return;
                    default:
                        MainActivity mainActivity2 = this.c;
                        int i4 = MainActivity.f1796e;
                        m2.c.d(mainActivity2, "this$0");
                        mainActivity2.startActivity(new Intent(mainActivity2, WifiDirectActivity.class));
                        return;
                }
            }
        });
        Button button2 = this.c;
        if (button2 == null) {
            c.g("buttonTicketGenerator");
            throw null;
        }
        button2.setOnClickListener(new b(1, this));
        Button button3 = this.f1798d;
        if (button3 != null) {
            button3.setOnClickListener(new View.OnClickListener(this) { // from class: a1.d
                public final /* synthetic */ MainActivity c;

                {
                    this.c = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (r2) {
                        case 0:
                            MainActivity mainActivity = this.c;
                            int i3 = MainActivity.f1796e;
                            m2.c.d(mainActivity, "this$0");
                            mainActivity.startActivity(new Intent(mainActivity, NumberCallerActivity.class));
                            return;
                        default:
                            MainActivity mainActivity2 = this.c;
                            int i4 = MainActivity.f1796e;
                            m2.c.d(mainActivity2, "this$0");
                            mainActivity2.startActivity(new Intent(mainActivity2, WifiDirectActivity.class));
                            return;
                    }
                }
            });
        } else {
            c.g("buttonWifiDirect");
            throw null;
        }
    }
}