package com.example.myapplication;

import a1.a;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import d.d;
import java.util.Arrays;
import java.util.List;
import m2.c;

/* loaded from: classes.dex */
public final class TicketGeneratorActivity extends d implements View.OnClickListener {

    /* renamed from: x  reason: collision with root package name */
    public static final /* synthetic */ int f1804x = 0;
    public List<int[]> u;

    /* renamed from: v  reason: collision with root package name */
    public boolean f1805v;

    /* renamed from: w  reason: collision with root package name */
    public LinearLayout f1806w;

    public TicketGeneratorActivity() {
        List<int[]> asList = Arrays.asList(new int[9], new int[9], new int[9]);
        c.c(asList, "asList(this)");
        this.u = asList;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        c.b(view);
        view.setBackgroundColor(-65536);
    }

    @Override // androidx.fragment.app.q, androidx.activity.ComponentActivity, v.d, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ticket_generator);
        View findViewById = findViewById(R.id.buttonGenerateTicket);
        c.c(findViewById, "findViewById(R.id.buttonGenerateTicket)");
        ((Button) findViewById).setOnClickListener(new a(1, this));
        View findViewById2 = findViewById(R.id.ticketLayout);
        c.c(findViewById2, "findViewById(R.id.ticketLayout)");
        this.f1806w = (LinearLayout) findViewById2;
        r();
    }

    public final void r() {
        Log.d("test", "generateBlocks() called");
        int size = this.u.size();
        int i3 = 0;
        while (i3 < size) {
            int i4 = i3 + 1;
            int length = this.u.get(i3).length;
            int i5 = 0;
            while (i5 < length) {
                int i6 = i5 + 1;
                if (i5 <= 4) {
                    this.u.get(i3)[i5] = 1;
                } else {
                    this.u.get(i3)[i5] = 0;
                }
                i5 = i6;
            }
            i3 = i4;
        }
        s();
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0207, code lost:
        if (r16.u.get(2)[r1] == 1) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0256, code lost:
        if (r16.u.get(2)[r1] == 0) goto L84;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void s() {
        /*
            Method dump skipped, instructions count: 1241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.myapplication.TicketGeneratorActivity.s():void");
    }
}