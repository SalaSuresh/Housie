package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.numbercaller.NumberCallerActivity
import com.example.myapplication.ticketgenerator.TicketGeneratorActivity
import com.example.myapplication.wifidirect.WifiDirectActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNumberCaller.setOnClickListener {
            startActivity(Intent(this@MainActivity, NumberCallerActivity::class.java))
        }

        binding.buttonTicketGenerator.setOnClickListener {
            startActivity(Intent(this@MainActivity, TicketGeneratorActivity::class.java))
        }

        binding.buttonWifiDirect.setOnClickListener {
            startActivity(Intent(this@MainActivity, WifiDirectActivity::class.java))
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })

    }

    private fun showExitDialog() {
        val exitDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        exitDialogBuilder.setMessage(R.string.do_you_want_to_exit)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.cancel()
                finish()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> //  Action for 'NO' Button
                dialog.cancel()
            }
        val exitDialog: AlertDialog = exitDialogBuilder.create()
        exitDialog.setTitle(R.string.alert)
        exitDialog.show()
    }
}