package com.example.myapplication.wifidirect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class WifiDirectActivity : AppCompatActivity() {
    lateinit var buttonClient: Button
    lateinit var buttonAdmin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_direct)

        buttonClient = findViewById(R.id.buttonClient)
        buttonClient.setOnClickListener {
            startActivity(Intent(this@WifiDirectActivity, ClientActivity::class.java))
        }
        buttonAdmin = findViewById(R.id.buttonAdmin)
        buttonAdmin.setOnClickListener {
            startActivity(Intent(this@WifiDirectActivity, AdminActivity::class.java))
        }
    }
}