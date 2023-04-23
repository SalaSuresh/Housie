package com.example.myapplication;

import a1.b;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import d.d;
import f2.a;
import java.util.ArrayList;
import java.util.Locale;
import m2.c;

/* loaded from: classes.dex */
public final class NumberCallerActivity extends d implements TextToSpeech.OnInitListener {
    public static final /* synthetic */ int D = 0;
    public int A;
    public TextView B;
    public GridLayout C;
    public TextToSpeech u;

    /* renamed from: v  reason: collision with root package name */
    public Button f1799v;

    /* renamed from: w  reason: collision with root package name */
    public RelativeLayout f1800w;

    /* renamed from: x  reason: collision with root package name */
    public int[] f1801x = new int[90];

    /* renamed from: y  reason: collision with root package name */
    public int[] f1802y = new int[90];

    /* renamed from: z  reason: collision with root package name */
    public final ArrayList<View> f1803z = new ArrayList<>();

    @Override // androidx.fragment.app.q, androidx.activity.ComponentActivity, v.d, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_number_caller);
        View findViewById = findViewById(R.id.layoutNumbers);
        c.c(findViewById, "findViewById(R.id.layoutNumbers)");
        this.f1800w = (RelativeLayout) findViewById;
        View findViewById2 = findViewById(R.id.textCalledNumber);
        c.c(findViewById2, "findViewById(R.id.textCalledNumber)");
        this.B = (TextView) findViewById2;
        View findViewById3 = findViewById(R.id.buttonCall);
        c.c(findViewById3, "findViewById(R.id.buttonCall)");
        Button button = (Button) findViewById3;
        this.f1799v = button;
        button.setOnClickListener(new b(2, this));
        TextToSpeech textToSpeech = new TextToSpeech(this, this);
        this.u = textToSpeech;
        textToSpeech.setLanguage(Locale.UK);
        int i3 = 0;
        while (i3 < 90) {
            int i4 = i3 + 1;
            this.f1801x[i3] = i4;
            i3 = i4;
        }
        int[] iArr = this.f1801x;
        this.f1802y = iArr;
        a.P(iArr, new n2.d(0));
        int i5 = 1;
        while (i5 < 91) {
            int i6 = i5 + 1;
            TextView textView = new TextView(this);
            textView.setTextSize(22.0f);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(layoutParams);
            textView.setText(String.valueOf(i5).length() == 1 ? c.f(Integer.valueOf(i5), "0") : String.valueOf(i5));
            this.f1803z.add(textView);
            i5 = i6;
        }
        this.C = new GridLayout(this);
        r().setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        r().setColumnCount(10);
        r().setRowCount(9);
        int size = this.f1803z.size();
        for (int i7 = 0; i7 < size; i7++) {
            GridLayout.LayoutParams layoutParams2 = new GridLayout.LayoutParams();
            layoutParams2.rowSpec = GridLayout.spec(Integer.MIN_VALUE, 1);
            layoutParams2.columnSpec = GridLayout.spec(Integer.MIN_VALUE, 1);
            r().addView(this.f1803z.get(i7));
        }
        RelativeLayout relativeLayout = this.f1800w;
        if (relativeLayout == null) {
            c.g("layoutNumbers");
            throw null;
        }
        relativeLayout.addView(r());
    }

    @Override // android.speech.tts.TextToSpeech.OnInitListener
    public final void onInit(int i3) {
        Log.d("test", c.f(Integer.valueOf(i3), "onInit() called with: status = "));
        if (i3 == 0) {
            TextToSpeech textToSpeech = this.u;
            if (textToSpeech == null) {
                c.g("textToSpeech");
                throw null;
            }
            int language = textToSpeech.setLanguage(Locale.US);
            if (language != -2 && language != -1) {
                return;
            }
            Log.d("test", "The Language not supported!");
        }
    }

    public final GridLayout r() {
        GridLayout gridLayout = this.C;
        if (gridLayout != null) {
            return gridLayout;
        }
        c.g("gridLayout");
        throw null;
    }
}